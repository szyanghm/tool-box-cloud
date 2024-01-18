package com.tool.box.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

import javax.servlet.Filter;
import java.util.Map;

/**
 * 处理资源路径中文乱码问题
 *
 * @Author v_haimiyang
 * @Date 2023/8/16 16:45
 * @Version 1.0
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Override
    protected FilterChainManager createFilterChainManager() {
        FilterChainManager manager = super.createFilterChainManager();
        Map<String, Filter> filterMap = manager.getFilters();
        Filter invalidRequestFilter = filterMap.get(DefaultFilter.invalidRequest.name());
        if (invalidRequestFilter instanceof InvalidRequestFilter) {
            //此处是关键，设置false跳过URL携带中文400，servletPath中文校验
            ((InvalidRequestFilter) invalidRequestFilter).setBlockNonAscii(false);
        }
        return manager;
    }

}
