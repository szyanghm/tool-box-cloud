package com.tool.box.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tool.box.utils.HttpUtils;
import com.tool.box.utils.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    Map params = Maps.newLinkedHashMap();


    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.tool.box.controller..*.*(..)) && !within(com.tool.box.controller.JobController)")
    public void webLog() {
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 开始打印请求日志
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        params.put("【url】", request.getRequestURL()); // 获取请求的url
        params.put("【method】", request.getMethod()); // 获取请求的方式
        params.put("【ip】", HttpUtils.getIpAddr(request)); // 获取请求的ip地址
        params.put("【className】", joinPoint.getSignature().getDeclaringTypeName()); // 获取类名
        params.put("【classMethod】", joinPoint.getSignature().getName()); // 获取类方法
        for (Object object : joinPoint.getArgs()) {
            if (
                    object instanceof MultipartFile
                            || object instanceof HttpServletRequest
                            || object instanceof HttpServletResponse
            ) {
                continue;
            }
            params.put("【request args】", object); // 请求参数
        }
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Exception {
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
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        try {
            params.put("【response args】", result); // 响应回包参数
            params.put("【spend time】",
                    (System.currentTimeMillis() - startTime.get()) + "ms"); // 响应回包参数
            startTime.remove();
        } catch (Exception ignore) {
        }
        return result;
    }
}






