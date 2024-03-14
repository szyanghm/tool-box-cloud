package com.tool.box.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 获取系统配置
 *
 * @Author v_haimiyang
 * @Date 2023/7/10 10:44
 * @Version 1.0
 */
@Configuration
public class SystemConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    /**
     * token密钥
     */
    public static String secret;
    public static Integer expire;

    /**
     * true拦截/false不拦截
     */
    public static Boolean enabled;
    /**
     * shiro拦截url
     */
    public static String definitions;

    @Value("${shiro.filter.chain.enabled}")
    public void getEnabled(Boolean enabled) {
        SystemConfig.enabled = enabled;
    }

    @Value("${jwt.expire}")
    public void getEnabled(Integer expire) {
        SystemConfig.expire = expire;
    }

    @Value("${jwt.secret}")
    public void getEnabled(String secret) {
        SystemConfig.secret = secret;
    }

    @Value("${shiro.filter.chain.definitions}")
    public void getDefinitions(String definitions) {
        SystemConfig.definitions = definitions;
    }


}
