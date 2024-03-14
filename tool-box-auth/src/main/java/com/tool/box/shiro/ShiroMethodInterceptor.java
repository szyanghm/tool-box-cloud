package com.tool.box.shiro;

import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.PermissionAnnotationMethodInterceptor;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限重定义
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 15:11
 * @Version 1.0
 */
public class ShiroMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {
    public ShiroMethodInterceptor() {
        super();
    }

    /**
     * 跳过shiro RequirePermission 注解验证
     */
    public ShiroMethodInterceptor skipPermissionHandler() {
        List<AuthorizingAnnotationMethodInterceptor> interceptors = this.getMethodInterceptors().stream()
                .filter(authorizingAnnotationMethodInterceptor ->
                        !(authorizingAnnotationMethodInterceptor instanceof PermissionAnnotationMethodInterceptor))
                .collect(Collectors.toList());
        PermissionAnnotationMethodInterceptor interceptor = new PermissionAnnotationMethodInterceptor();
        //设置成自己的注解处理器!
        interceptor.setHandler(new ShiroPermissionHandler());
        interceptors.add(interceptor);
        setMethodInterceptors(interceptors);
        return this;
    }

}
