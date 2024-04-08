package com.tool.box.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tool.box.common.Contents;
import com.tool.box.common.SystemUrl;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.HttpUtils;
import com.tool.box.utils.RedisUtils;
import com.tool.box.utils.SystemUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    Map<Object, Object> params = Maps.newLinkedHashMap();

    /**
     * 以 controller 包下定义的所有请求为切入点,排除扫描定时任务控制器
     */
    @Pointcut("execution(public * com.tool.box.controller..*.*(..)))")
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
        String ip = HttpUtils.getIpAddr(request);
        params.put("【url】", url); // 获取请求的url
        params.put("【method】", request.getMethod()); // 获取请求的方式
        params.put("【ip】", ip); // 获取请求的ip地址
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
        //防止暴力请求
        isAllowed(ip);
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

    private void verifyLogin(String url, String ip, Object obj) {
        if (url.contains(SystemUrl.login_url) && ObjectUtil.isNotNull(obj)) {
            String str = String.valueOf(obj);
            LoginDTO dto = JSONObject.parseObject(str, LoginDTO.class);
            setErrorNum(ip + dto.getAccount());
        }
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
        if (!url.contains(SystemUrl.login_url) && CollectionUtil.isNotEmpty(params)) {
            List<String> list = new ArrayList<>();
            String token;
            if (url.contains(SystemUrl.register_url)) {
                //新用户注册没token,使用ip作为唯一
                token = HttpUtils.getIpAddr(request);
            } else {
                Method method = SystemUtils.getCurrentMethod(joinPoint);
                //当需要新增和更新权限的时候才需要进行校验
                RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);
                if (ObjectUtil.isNull(permissions)) {
                    return;
                }
                String[] arr = permissions.value();
                list = Arrays.asList(arr);
                //获取token
                token = request.getHeader(Contents.X_ACCESS_TOKEN);
            }
            synchronized (this) {
                //通过token获取上一次的参数
                String backParams = redisUtils.get(Contents.CHECK_PARAMS_TOKEN + token);
                //当前请求参数
                String currentParams = SecureUtil.md5(JSONObject.toJSONString(params));
                //参数对比
                if (!currentParams.equals(backParams)
                        && (list.contains(Contents.OP_WRITE_ADD)
                        || list.contains(Contents.OP_WRITE_UPDATE)
                        || url.contains(SystemUrl.register_url))) {
                    //将token作为key,缓存请求参数
                    redisUtils.set(Contents.CHECK_PARAMS_TOKEN + token, currentParams, 2L);
                } else {
                    //参数重复表示重复提交
                    throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY_RESUBMIT);
                }
            }
        }
    }

    /**
     * 限制同一个ip的访问次数
     *
     * @param ip ip地址
     */
    public void isAllowed(String ip) {
        long count = redisUtils.incr(Contents.IP_KEY + ip);
        if (count == 1) {
            redisUtils.expire(Contents.IP_KEY + ip, 1, TimeUnit.MINUTES); // 如果是第一次访问，设置过期时间
        }
        if (count >= Contents.MAX_REQUESTS) {
            //参数重复表示重复提交
            throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY);
        }
    }

    /**
     * 限制同一个账号登录的时候出错次数
     *
     * @param account 账号
     */
    public void setErrorNum(String account) {
        long count = redisUtils.incr(account + Contents.error_num);
        if (count >= Contents.MAX_LOGIN) {
            redisUtils.expire(account + Contents.error_num, 1, TimeUnit.HOURS); // 如果是第一次访问，设置过期时间
        }
    }

    public void isErrorNum(String account) {
        long count = redisUtils.incr(account + Contents.error_num);
        if (count >= Contents.MAX_LOGIN) {
            throw new InternalApiException(SystemCodeEnum.USER_ACCOUNT_LOCK_ING);
        }
    }
}






