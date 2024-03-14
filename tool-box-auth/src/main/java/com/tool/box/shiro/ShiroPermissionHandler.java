package com.tool.box.shiro;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * 重写@RequiresPermissions处理方法
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 15:13
 * @Version 1.0
 */
@Slf4j
public class ShiroPermissionHandler extends PermissionAnnotationHandler {

    public ShiroPermissionHandler() {
        super();
        log.warn("使用了自定义的PermissionHandler,如果是生产环境,使用这个类将会导致权限控制模块失效");
    }

    /**
     * 重写权限认证方法,仅仅打印log,不做拦截处理
     *
     * @param a 注解
     * @throws AuthorizationException 一个不可能抛出的异常
     */
    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof RequiresPermissions)) return;
        //如果是数组,打印效果不好,使用json序列化
        log.warn("警告!!   跳过了权限:{}", JSONObject.toJSONString(getAnnotationValue(a)));
    }

}
