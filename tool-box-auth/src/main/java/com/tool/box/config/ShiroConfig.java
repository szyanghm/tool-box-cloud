package com.tool.box.config;

import com.tool.box.cache.RedisCacheManager;
import com.tool.box.shiro.EnhanceSessionManager;
import com.tool.box.shiro.RedisSessionDAO;
import com.tool.box.shiro.ShiroSessionProperties;
import com.tool.box.shiro.UserRealm;
import com.tool.box.utils.SystemUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ShiroSessionProperties shiroSessionProperties;

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    /**
     * 会话ID生成器
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * Shiro生命周期处理器</br>
     * LifecycleBeanPostProcessor用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调，
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调。
     * 如UserRealm就实现了Initializable，而DefaultSecurityManager实现了Destroyable。
     *
     * @return LifecycleBeanPostProcessor
     */
    @Bean
    @ConditionalOnMissingBean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //endregion

    //region shiro AOP支持

    /**
     * DefaultAdvisorAutoProxyCreator是用来扫描上下文，寻找所有的Advisor(通知器）
     * 将这些Advisor应用到所有符合切入点的Bean中
     * 所以必须在lifecycleBeanPostProcessor创建之后创建
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    /**
     * 提供shiro权限注解支持
     * 该通知器用于匹配加了认证注解的类与方法
     *
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager());
        return advisor;
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

    /**
     * 提供sessionDao实现
     *
     * @return sessionDAO
     */
    @Bean
    @ConditionalOnMissingBean
    public AbstractSessionDAO sessionDAO() {
        AbstractSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }

    /**
     * 注入配置url过滤器
     */
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

    /**
     * 设置用于匹配密码的CredentialsMatcher
     *
     * @return credentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
        credentialsMatcher.setHashIterations(1024);  // 散列迭代次数
        return credentialsMatcher;
    }

    /**
     * 创建安全管理器
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //自定义Realm
        defaultWebSecurityManager.setRealm(userRealm());
        //自定义session管理器
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        return defaultWebSecurityManager;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager enhanceSessionManager = new EnhanceSessionManager();
        // 会话过期删除会话
        enhanceSessionManager.setDeleteInvalidSessions(true);
        enhanceSessionManager.setGlobalSessionTimeout(shiroSessionProperties.getTimeOut() * 60000L);
        // 定时检查失效的session
        enhanceSessionManager.setSessionValidationSchedulerEnabled(true);
        // 设置sessionDao(可以选择具体session存储方式)
        enhanceSessionManager.setSessionDAO(sessionDAO());
        return enhanceSessionManager;
    }

}
