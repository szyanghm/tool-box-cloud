package com.tool.box.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author v_haimiyang
 * @Date 2023/7/10 10:44
 * @Version 1.0
 */
@Component
public class SystemConfig {

    public static String secret;

    public static Integer expire;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expire}")
    private String jwtExpire;


    @PostConstruct
    public void init() {
        secret = this.jwtSecret;
        expire = Integer.parseInt(this.jwtExpire);
    }

}
