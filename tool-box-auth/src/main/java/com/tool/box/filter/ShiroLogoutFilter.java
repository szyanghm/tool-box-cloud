package com.tool.box.filter;

import com.tool.box.shiro.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author v_haimiyang
 * @Date 2023/7/11 15:39
 * @Version 1.0
 */
public class ShiroLogoutFilter extends LogoutFilter {

    /**
     * redis使用模板
     */
    @Resource(name = "redisTemplateForCache")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
        PrincipalCollection principals = subject.getPrincipals();
        shiroRealm.clearCachedAuthorizationInfo(principals);
        String userName = subject.getPrincipal().toString();
        // 在这进行删除缓存操作
        redisTemplate.opsForHash().delete(shiroRealm.getAuthenticationCacheName(), userName);
        // 登出
        subject.logout();
        // 获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request, response, subject);
        // 重定向
        issueRedirect(request, response, redirectUrl);
        return false;
    }
}
