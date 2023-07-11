package com.tool.box.config;

import com.tool.box.cache.RedisCacheManager;
import com.tool.box.shiro.EnhanceSessionManager;
import com.tool.box.shiro.RedisSessionDAO;
import com.tool.box.shiro.UserRealm;
import com.tool.box.utils.SystemUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * shiro配置
 *
 * @Author v_haimiyang
 * @Date 2023/7/7 10:03
 * @Version 1.0
 */
@Component
@Configuration
public class ShiroConfig {

    @Value("${shiro.filter.chain.definitions}")
    private String definitions;

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }


    // 配置自定义Realm
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(redisCacheManager());
        // 开启全局缓存
        userRealm.setCachingEnabled(true);
        // 开启认证缓存
        userRealm.setAuthenticationCachingEnabled(true);
        // 开启授权缓存
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setCredentialsMatcher(credentialsMatcher()); //配置使用哈希密码匹配
        return userRealm;
    }

//    /**
//     * RedisSessionDAO shiro sessionDao层的实现 通过redis
//     * 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }

    /**
     * 提供sessionDao实现
     *
     * @return sessionDAO
     */
    @Bean
    @ConditionalOnMissingBean
    public AbstractSessionDAO sessionDAO() {
        AbstractSessionDAO sessionDAO = new RedisSessionDAO();
        return sessionDAO;
    }

    // 配置url过滤器
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        Map<String, String> map = SystemUtils.getMapData(definitions, ";");
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("/captcha", "anon");
//        chainDefinition.addPathDefinition("/logout", "anon");
//        chainDefinition.addPathDefinition("/layuiadmin/**", "anon");
//        chainDefinition.addPathDefinition("/druid/**", "anon");
//        chainDefinition.addPathDefinition("/api/**", "anon");
//        chainDefinition.addPathDefinition("/login", "anon");
//        chainDefinition.addPathDefinition("/**", "authc");
        chainDefinition.addPathDefinitions(map);
        return chainDefinition;
    }

    // 设置用于匹配密码的CredentialsMatcher
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
        credentialsMatcher.setHashIterations(1024);  // 散列迭代次数
        return credentialsMatcher;
    }

    // 配置security并设置userReaml，避免xxxx required a bean named 'authorizer' that could not be found.的报错
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(defaultWebSessionManager());
        // securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager enhanceSessionManager = new EnhanceSessionManager();
        // 会话过期删除会话
        enhanceSessionManager.setDeleteInvalidSessions(true);
        // 定时检查失效的session
        enhanceSessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置sessionDao(可以选择具体session存储方式)
        enhanceSessionManager.setSessionDAO(sessionDAO());
        return enhanceSessionManager;
    }

}
