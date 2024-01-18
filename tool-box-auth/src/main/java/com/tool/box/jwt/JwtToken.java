package com.tool.box.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author v_haimiyang
 * @Date 2023/8/15 17:22
 * @Version 1.0
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
