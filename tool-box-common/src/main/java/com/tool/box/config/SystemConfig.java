package com.tool.box.config;

import com.tool.box.common.Contents;
import com.tool.box.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 获取系统配置
 *
 * @Author v_haimiyang
 * @Date 2023/7/10 10:44
 * @Version 1.0
 */
@Configuration
public class SystemConfig {

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
    public static Map<String, String> definitionsMap;

    /**
     * 服务名
     */
    public static String applicationName;

    @Value("${spring.application.name}")
    public void getApplicationName(String applicationName) {
        SystemConfig.applicationName = applicationName;
    }

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
        SystemConfig.definitionsMap = SystemUtils.getMapData(enabled, definitions, Contents.SEMICOLON);
    }

}
