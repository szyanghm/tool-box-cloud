package com.tool.box.shiro;

import com.tool.box.api.CommonAPI;
import com.tool.box.base.LoginUser;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.jwt.JwtToken;
import com.tool.box.utils.JwtUtils;
import com.tool.box.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户登录鉴权和获取用户授权
 *
 * @Author v_haimiyang
 * @Date 2023/7/5 16:13
 * @Version 1.0
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Resource
    private CommonAPI commonAPI;

    @Resource
    private TokenUtils tokenUtils;


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("===============Shiro权限认证开始============ [ roles、permissions]==========");
        if (principalCollection == null) {
            throw new InternalApiException(SystemCodeEnum.TOKEN_EXCEPTION);
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        LoginUser loginUser = (LoginUser) principalCollection.getPrimaryPrincipal();
        // 角色
        Set<String> roles = new HashSet<>();
        roles.add(loginUser.getRoleCode());
        List<String> permissions = commonAPI.getPermissions(loginUser.getRoleCode());
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(new HashSet<>(permissions));
        log.info("===============Shiro权限认证成功==============");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.debug("===============Shiro身份认证开始============doGetAuthenticationInfo==========");
        String token = (String) authenticationToken.getCredentials();
        if (StringUtils.isBlank(token)) {
            throw new InternalApiException(SystemCodeEnum.TOKEN_EXCEPTION);
        }
        // 校验token有效性
        LoginUser loginUser = this.checkJwtTokenRefresh(token);
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }


    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }


    /**
     * 校验token&刷新token
     *
     * @param token 登录凭证
     * @return 用户信息
     */
    public LoginUser checkJwtTokenRefresh(String token) {
        if (!JwtUtils.verify(token)) {
            throw new InternalApiException(SystemCodeEnum.INVALID_TOKEN);
        }
        String account;
        try {
            // 解密获得username，用于和数据库进行对比
            account = JwtUtils.getAccount(token);
        } catch (Exception e) {
            throw new InternalApiException(SystemCodeEnum.INVALID_TOKEN);
        }
        if (StringUtils.isBlank(account)) {
            throw new InternalApiException(SystemCodeEnum.TOKEN_EXCEPTION);
        }
        //如果redis取不到表示用户过期了
        String authToken = tokenUtils.getAuthToken(account);
        if (StringUtils.isBlank(authToken)) {
            throw new InternalApiException(SystemCodeEnum.USER_LOGIN_EXPIRED);
        }
        LoginUser loginUser = JwtUtils.getToken(authToken);
        // 拿到了，校验token有效性
        if (!tokenUtils.verifyExpired(account)) {
            //token续期
            tokenUtils.setToken(authToken, account);
            log.debug("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— " + token);
        } else {
            if (loginUser == null) {
                throw new InternalApiException(SystemCodeEnum.USER_DOES_NOT_EXIST);
            }
            if (loginUser.getStatus() != 0) {
                throw new InternalApiException(SystemCodeEnum.USER_LOCK_ING);
            }
        }
        return loginUser;
    }

}
