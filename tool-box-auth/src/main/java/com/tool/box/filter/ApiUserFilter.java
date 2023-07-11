//package com.tool.box.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.core.annotation.Order;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
///**
// * @Author v_haimiyang
// * @Date 2023/7/5 16:18
// * @Version 1.0
// */
//@Order(1)
//@WebFilter(filterName = "apiUserFilter", urlPatterns = "/*")
//@Slf4j
//public class ApiUserFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(
//                httpServletRequest);
//        String token = httpServletRequest.getHeader("token");
//        if (!StringUtils.isEmpty(token)) {
//            // 当token不为空时,API接口方式执行登录,使用获取的token执行校验
//            // 以下request设置为标记session来源相关等
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//        }
//        filterChain.doFilter(mutableRequest, servletResponse);
//    }
//}
