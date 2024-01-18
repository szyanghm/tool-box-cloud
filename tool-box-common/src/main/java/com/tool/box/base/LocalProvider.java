package com.tool.box.base;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 业务代码中直接使用全局此类，获取用户登入态信息
 * */
@Slf4j
public class LocalProvider {

    private static final ThreadLocal<ContextProvider> CONTEXT_PROVIDER = new ThreadLocal<>();
    private static final ThreadLocal<UserInfo> USER_INFO = new ThreadLocal<>();

    public static void init(HttpServletRequest request, HttpServletResponse response,
            UserInfo userInfo) {
        CONTEXT_PROVIDER.set(new ContextProvider(request, response));
        USER_INFO.set(userInfo);
    }

    public static UserInfo getUser() {
        return USER_INFO.get();
    }

    public static void destroy() {
        CONTEXT_PROVIDER.remove();
        USER_INFO.remove();
    }

    private static boolean isNull() {
        return USER_INFO.get() == null;
    }

    public static HttpServletRequest getRequest() {
        return CONTEXT_PROVIDER.get().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return CONTEXT_PROVIDER.get().getResponse();
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }
}