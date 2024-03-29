package com.tool.box.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tool.box.common.Contents;
import com.tool.box.config.SystemConfig;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.HttpUtils;
import com.tool.box.utils.RedisUtils;
import com.tool.box.utils.SystemUtils;
import com.tool.box.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * aop切面处理
 *
 * @Author v_haimiyang
 * @Date 2023/12/29 16:24
 * @Version 1.0
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private SystemConfig systemConfig;

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    Map<Object, Object> params = Maps.newLinkedHashMap();

    /**
     * 以 controller 包下定义的所有请求为切入点,排除扫描定时任务控制器
     */
    @Pointcut("execution(public * com.tool.box.controller..*.*(..)) && !within(com.tool.box.controller.JobController)")
    public void webLog() {
    }

    /**
     * 在切点之前植入
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        params.put("【url】", url); // 获取请求的url
        params.put("【method】", request.getMethod()); // 获取请求的方式
        params.put("【ip】", HttpUtils.getIpAddr(request)); // 获取请求的ip地址
        params.put("【className】", joinPoint.getSignature().getDeclaringTypeName()); // 获取类名
        params.put("【classMethod】", joinPoint.getSignature().getName()); // 获取类方法
        for (Object object : joinPoint.getArgs()) {
            if (object instanceof MultipartFile
                    || object instanceof HttpServletRequest
                    || object instanceof HttpServletResponse) {
                continue;
            }
            params.put("【request args】", object); // 请求参数
        }
        //校验重复提交
        verifyResubmit(url, joinPoint, request);
    }

    /**
     * 在切点之后植入
     */
    @After("webLog()")
    public void doAfter() {
        // 输出格式化后的json字符串
        try {
            log.info(JSONObject.toJSONString(params));
        } catch (Exception e) {
            log.error("fail to pares params,e:{}", SystemUtils.getErrorMessage(e));
        }
        //清空每次内容
        params = Maps.newLinkedHashMap();
        // 每个请求之间空一行
        log.info("");
    }

    /**
     * 环绕
     *
     * @return 参数
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        try {
            params.put("【response args】", result); // 响应回包参数
            params.put("【spend time】",
                    (System.currentTimeMillis() - startTime.get()) + "ms"); // 响应回包参数
            startTime.remove();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY);
        }
        return result;
    }

    /**
     * 防抖校验重复提交
     * 只有带保存和更新权限的接口才进行校验
     *
     * @param url       请求接口地址
     * @param joinPoint aop入参
     * @param request   http请求体
     */
    private void verifyResubmit(String url, JoinPoint joinPoint, HttpServletRequest request) {
        if (!url.contains("login") && CollectionUtil.isNotEmpty(params)) {
            Method method = SystemUtils.getCurrentMethod(joinPoint);
            //当需要新增和更新权限的时候才需要进行校验
            RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);
            if (ObjectUtil.isNull(permissions)) {
                return;
            }
            String[] arr = permissions.value();
            List<String> list = Arrays.asList(arr);
            //获取token
            String token = request.getHeader(Contents.X_ACCESS_TOKEN);
            synchronized (this) {
                //通过token获取上一次的参数
                String backParams = redisUtils.get(token);
                //当前请求参数
                String currentParams = SecureUtil.md5(JSONObject.toJSONString(params));
                //参数对比
                if (!currentParams.equals(backParams)
                        && (list.contains(Contents.OP_WRITE_ADD) || list.contains(Contents.OP_WRITE_UPDATE))) {
                    //将token作为key,缓存请求参数
                    redisUtils.set(token, currentParams, 2L);
                } else {
                    //参数重复表示重复提交
                    throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY_RESUBMIT);
                }
            }
        }
    }
}






