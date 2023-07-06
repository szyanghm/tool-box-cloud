package com.tool.box.shiro;

import com.tool.box.base.UserInfo;
import com.tool.box.feign.UserInfoConsumer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author v_haimiyang
 * @Date 2023/7/5 16:12
 * @Version 1.0
 */
public class AdminUsernamePasswordRealm extends AuthorizingRealm {

    @Resource
    private UserInfoConsumer userInfoConsumer;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole());
        Set<String> permissions = userInfoConsumer.getPermissions(user.getRole());
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        UserInfo userInfo = userInfoConsumer.getUserInfo(account);
        return new SimpleAuthenticationInfo(userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getSalt()),
                getName());
    }
}
