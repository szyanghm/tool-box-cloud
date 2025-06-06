package com.tool.box.aspect;

import cn.hutool.core.text.StrPool;
import com.tool.box.common.Contents;
import com.tool.box.common.validata.LoginLimit;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.HttpUtils;
import com.tool.box.utils.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 登录错误次数限制
 *
 * @Author v_haimiyang
 * @Date 2024/4/7 16:54
 * @Version 1.0
 */
@Aspect
@Component
public class LoginLimitAspect {

    @Resource
    private RedisUtils redisUtils;

    // 切点定义，匹配带有LoginLimit注解的方法
    @Pointcut(value = "@annotation(com.tool.box.common.validata.LoginLimit)")
    public void loginPointcut() {
    }

    // 环绕通知，在目标方法执行前执行
    @Around("loginPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] objects = joinPoint.getArgs();
        // 这个方法需要你自己实现，获取账号信息
        LoginDTO dto = (LoginDTO) objects[0];
        LoginLimit loginLimit = method.getAnnotation(LoginLimit.class);
        int limit = loginLimit.limit();
        // 获取登录者的IP和账号
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = HttpUtils.getIpAddr(request);

        String key = ip + StrPool.COLON + dto.getAccount();
        isErrorNum(key, limit);
        Object result;
        try {
            // 执行原方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            // 登录失败，次数增加
            setErrorNum(key, limit);
            throw e;
        }
        // 登录成功，清除计数
        redisUtils.del(key);
        return result;
    }

    /**
     * 限制同一个ip,同一个账号登录的时候出错次数
     *
     * @param account 账号
     */
    public void setErrorNum(String account, int limit) {
        long count = redisUtils.incr(Contents.LOGIN_FAIL_COUNT + account);
        if (count >= limit) {
            redisUtils.expire(Contents.LOGIN_FAIL_COUNT + account, 1, TimeUnit.HOURS);
        }
    }

    public void isErrorNum(String account, int limit) {
        Integer count = redisUtils.get(Contents.LOGIN_FAIL_COUNT + account);
        if (count >= limit) {
            throw new InternalApiException(SystemCodeEnum.USER_ACCOUNT_LOCK_ING);
        }
    }

}
