package com.tool.box.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author v_haimiyang
 * @Date 2023/7/5 16:17
 * @Version 1.0
 */
@Order(1)
@WebFilter(filterName = "adminUserFilter", urlPatterns = "/*")
@Slf4j
public class AdminUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


    }
}
