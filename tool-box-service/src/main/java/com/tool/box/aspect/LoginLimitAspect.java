package com.tool.box.aspect;

import com.tool.box.common.Contents;
import com.tool.box.common.validata.LoginLimit;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.HttpUtils;
import com.tool.box.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
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

        String key = "login_fail_count:" + ip + ":" + dto.getAccount();
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
     * 限制同一个账号登录的时候出错次数
     *
     * @param account 账号
     */
    public void setErrorNum(String account, int limit) {
        long count = redisUtils.incr(account + Contents.error_num);
        if (count >= limit) {
            redisUtils.expire(account + Contents.error_num, 1, TimeUnit.HOURS); // 如果是第一次访问，设置过期时间
        }
    }

    public void isErrorNum(String account, int limit) {
        String str = redisUtils.get(account + Contents.error_num);
        if (StringUtils.isNotBlank(str)) {
            int count = Integer.parseInt(str);
            if (count >= limit) {
                throw new InternalApiException(SystemCodeEnum.USER_ACCOUNT_LOCK_ING);
            }
        }
    }

}
