package com.tool.box.config;

import com.tool.box.filter.AdminUserFilter;
import com.tool.box.filter.ApiUserFilter;
import com.tool.box.listener.AuthenticationLogListener;
import com.tool.box.shiro.AdminUsernamePasswordRealm;
import com.tool.box.shiro.ApiUsernamePasswordRealm;
import me.sdevil507.supports.shiro.matcher.PasswordRetryHashedCredentialsMatcher;
import me.sdevil507.supports.shiro.properties.ShiroProperties;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * shiro权限框架配置
 *
 * @Author v_haimiyang
 * @Date 2023/7/5 16:02
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(name = "shiro.enable", matchIfMissing = true)
public class ShiroConfiguration {

    //region 参数
    /**
     * shiro配置参数
     */
    @Autowired
    private ShiroProperties shiroProperties;

    /**
     * 缓存管理器
     */
    @Autowired
    @Qualifier("shiroCacheManager")
    private CacheManager shiroCacheManager;
    //endregion

    //region 拓展监听器入口

    /**
     * [拓展功能]监听器集合
     *
     * @return listeners
     */
    @Bean
    public List<AuthenticationListener> listeners() {
        List<AuthenticationListener> listeners = new ArrayList<>();
        listeners.add(authenticationLogListener());
        return listeners;
    }

    /**
     * 认证监听器
     *
     * @return 身份认证监听器
     */
    @Bean(name = "authenticationLogListener")
    public AuthenticationListener authenticationLogListener() {
        return new AuthenticationLogListener();
    }
    //endregion

    //region 多realm方式登录拓展入口

    /**
     * [拓展功能]多realm集合
     *
     * @return realms
     */
    @Bean
    public List<Realm> realms() {
        List<Realm> realms = new ArrayList<>();
        realms.add(adminUsernamePasswordRealm());
        realms.add(apiUsernamePasswordRealm());
        return realms;
    }

    /**
     * 域，Shiro从Realm获取安全数据（如用户、角色、权限）<br>
     * SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；<br>
     * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；
     *
     * @return AdminUserRealm
     */
    @Bean(name = "adminUsernamePasswordRealm")
    public AdminUsernamePasswordRealm adminUsernamePasswordRealm() {
        AdminUsernamePasswordRealm adminUsernamePasswordRealm = new AdminUsernamePasswordRealm();
        // 注入凭证匹配器
        adminUsernamePasswordRealm.setCredentialsMatcher(adminPasswordRetryHashedCredentialsMatcher());
        // 启用缓存，默认false
        adminUsernamePasswordRealm.setCachingEnabled(true);
        // 打开身份认证缓存并设置缓存名称
        adminUsernamePasswordRealm.setAuthenticationCachingEnabled(true);
        adminUsernamePasswordRealm.setAuthenticationCacheName("shiro-admin-username-authenticationCache");
        // 打开授权缓存并设置缓存名称
        adminUsernamePasswordRealm.setAuthorizationCachingEnabled(true);
        adminUsernamePasswordRealm.setAuthorizationCacheName("shiro-admin-username-authorizationCache");
        return adminUsernamePasswordRealm;
    }

    /**
     * 接口方式登录用户验证域
     *
     * @return 验证域
     */
    @Bean(name = "apiUsernamePasswordRealm")
    public ApiUsernamePasswordRealm apiUsernamePasswordRealm() {
        ApiUsernamePasswordRealm apiUsernamePasswordRealm = new ApiUsernamePasswordRealm();
        // 注入凭证匹配器
        apiUsernamePasswordRealm.setCredentialsMatcher(apiPasswordRetryHashedCredentialsMatcher());
        // 启用缓存，默认false
        apiUsernamePasswordRealm.setCachingEnabled(true);
        // 打开身份认证缓存并设置缓存名称
        apiUsernamePasswordRealm.setAuthenticationCachingEnabled(true);
        apiUsernamePasswordRealm.setAuthenticationCacheName("shiro-api-username-authenticationCache");
        // 打开授权缓存并设置缓存名称
        apiUsernamePasswordRealm.setAuthorizationCachingEnabled(true);
        apiUsernamePasswordRealm.setAuthorizationCacheName("shiro-api-username-authorizationCache");
        return apiUsernamePasswordRealm;
    }

    /**
     * 拓展凭证匹配器,继承自HashedCredentialsMatcher<br>
     * HashedCredentialsMatcher会根据配置自动识别盐值,加入登录错误次数限制
     *
     * @return RetryLimitHashedCredentialsMatcher
     */
    @Bean(name = "adminPasswordRetryHashedCredentialsMatcher")
    public PasswordRetryHashedCredentialsMatcher adminPasswordRetryHashedCredentialsMatcher() {
        PasswordRetryHashedCredentialsMatcher adminPasswordRetryHashedCredentialsMatcher = new PasswordRetryHashedCredentialsMatcher(shiroCacheManager, "admin-shiro-passwordRetryCache", shiroProperties.getPassword().getRetryCount(), shiroProperties.getPassword().getLockTime());
        adminPasswordRetryHashedCredentialsMatcher.setHashAlgorithmName("md5");
        adminPasswordRetryHashedCredentialsMatcher.setHashIterations(1);
        adminPasswordRetryHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return adminPasswordRetryHashedCredentialsMatcher;
    }

    /**
     * api用户凭证匹配器
     *
     * @return RetryLimitHashedCredentialsMatcher
     */
    @Bean(name = "apiPasswordRetryHashedCredentialsMatcher")
    public PasswordRetryHashedCredentialsMatcher apiPasswordRetryHashedCredentialsMatcher() {
        PasswordRetryHashedCredentialsMatcher apiPasswordRetryHashedCredentialsMatcher = new PasswordRetryHashedCredentialsMatcher(shiroCacheManager, "api-shiro-passwordRetryCache", shiroProperties.getPassword().getRetryCount(), shiroProperties.getPassword().getLockTime());
        apiPasswordRetryHashedCredentialsMatcher.setHashAlgorithmName("md5");
        apiPasswordRetryHashedCredentialsMatcher.setHashIterations(1);
        apiPasswordRetryHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return apiPasswordRetryHashedCredentialsMatcher;
    }
    //endregion

    //region [核心部分]shiro filter配置

    /**
     * shiro过滤器,用于拦截配置中的请求并进行链式处理
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录url
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 注意此处的hashMap中int值，如果有新增filter超过个数，切记同时更改
        Map<String, Filter> filters = new HashMap<>(20);
        // 此处需要new自定义filter,如果注册为Bean交给Spring管理的话,
        // 会被自动注册至Spring的FilterChain中,被消费掉,导致无法加载
        filters.put("adminUser", new AdminUserFilter());
        filters.put("apiUser", new ApiUserFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 此处范围如有重叠(例如：/admin/** 包含 /admin/logout与/admin/ajaxLogin)，切记从小至大配置
        // 否则ant匹配规则会导致永远不会调用小范围的filter
        // 同一url匹配规则下多个filter验证，使用","分隔，并且注意顺序[例如:("/admin/**","xxx,user")]
        // 则先执行xxx filter，再执行user filter
        filterChainDefinitionMap.put("/admin/logout", "anon");
        filterChainDefinitionMap.put("/admin/ajaxLogin", "anon");
        // 支持"验证通过"和"记住我"访问
        filterChainDefinitionMap.put("/admin/**", "adminUser");
        // api验证相关
        filterChainDefinitionMap.put("/api/logout", "anon");
        filterChainDefinitionMap.put("/api/login", "anon");
        filterChainDefinitionMap.put("/api/user/register", "anon");
        filterChainDefinitionMap.put("/api/test/**", "anon");
        filterChainDefinitionMap.put("/api/**", "apiUser");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
