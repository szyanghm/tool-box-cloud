package com.tool.box.config;

import com.tool.box.common.Contents;
import com.tool.box.common.DataStatic;
import com.tool.box.filter.JwtFilter;
import com.tool.box.shiro.CustomShiroFilterFactoryBean;
import com.tool.box.shiro.ShiroMethodInterceptor;
import com.tool.box.shiro.ShiroRealm;
import com.tool.box.utils.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * shiro配置
 *
 * @Author v_haimiyang
 * @Date 2023/7/7 10:03
 * @Version 1.0
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;
    @Resource
    private SystemConfig systemConfig;

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
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager defaultWebSecurityManager, Environment environment) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        //获取当前运行环境
        String profile = environment.getRequiredProperty(Contents.PROFILE);
        //判断是否可以跳过shiro
        if (!systemConfig.enabled && DataStatic.profileDataList.contains(profile)) {
            //运行跳过shiro权限验证方法
            advisor.setAdvice(new ShiroMethodInterceptor().skipPermissionHandler());
        }
        return advisor;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return redis缓存管理器
     */
    public RedisCacheManager redisCacheManager() {
        log.info("===============(1)创建缓存管理器RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("account");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(2000);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return redis管理器
     */
    @Bean
    public IRedisManager redisManager() {
        log.info("===============(2)创建RedisManager,连接Redis..");
        IRedisManager manager;
        // redis 单机支持，在集群为空，或者集群无机器时候使用 add by jzyadmin@163.com
        if (lettuceConnectionFactory.getClusterConfiguration() == null
                || lettuceConnectionFactory.getClusterConfiguration().getClusterNodes().isEmpty()) {
            RedisManager redisManager = new RedisManager();
            redisManager.setHost(lettuceConnectionFactory.getHostName() + ":" + lettuceConnectionFactory.getPort());
            redisManager.setDatabase(lettuceConnectionFactory.getDatabase());
            redisManager.setTimeout(0);
            if (StringUtils.isNotBlank(lettuceConnectionFactory.getPassword())) {
                redisManager.setPassword(lettuceConnectionFactory.getPassword());
            }
            manager = redisManager;
        } else {
            // redis集群支持，优先使用集群配置
            RedisClusterManager redisManager = new RedisClusterManager();
            Set<HostAndPort> portSet = new HashSet<>();
            lettuceConnectionFactory.getClusterConfiguration().getClusterNodes().forEach(node
                    -> portSet.add(new HostAndPort(node.getHost(), node.getPort())));
            if (StringUtils.isNotBlank(lettuceConnectionFactory.getPassword())) {
                JedisCluster jedisCluster = new JedisCluster(portSet, Contents.NUM_2000, Contents.NUM_2000
                        , Contents.NUM_5, lettuceConnectionFactory.getPassword(), new GenericObjectPoolConfig<>());
                redisManager.setPassword(lettuceConnectionFactory.getPassword());
                redisManager.setJedisCluster(jedisCluster);
            } else {
                JedisCluster jedisCluster = new JedisCluster(portSet);
                redisManager.setJedisCluster(jedisCluster);
            }
            manager = redisManager;
        }
        return manager;
    }


    /**
     * 注入配置url过滤器
     *
     * @return shiro过滤器工厂
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        CustomShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(Contents.JWT_FILTER, new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(SystemUtils.getMapData(systemConfig.enabled
                , systemConfig.definitions, Contents.SEMICOLON));
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建安全管理器
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //自定义Realm
        defaultWebSecurityManager.setRealm(shiroRealm);
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }

}
