package com.tool.box.utils;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http管理工具类
 *
 * @Author v_haimiyang
 * @Date 2023/4/19 15:43
 * @Version 1.0
 */
@Slf4j
public class HttpUtils {

    /**
     * 不超时
     */
    private static final int TIMEOUT = -1;

    /**
     * 30s超时
     */
    private static final int HALF_MIN = 30;

    /**
     * 60s超时
     */
    private static final int MIN = 60;

    public static String post(String url, String body) {
        return post(url, body, TIMEOUT);
    }

    public static String post(String url, String body, int timeout) {
        log.info("http post url:{},data:{}", url, body);
        String result = HttpUtil.post(url, body, timeout);
        log.info("get http result:{}", result);
        return result;
    }

    public static String get(String url) {
        return get(url, TIMEOUT);
    }

    public static String get(String url, int timeout) {
        log.info("http get url:{}", url);
        String result = HttpUtil.get(url, timeout);
        log.info("get http result:{}", result);
        return result;
    }

    private static Proxy getProxy(String agentHost, Integer agentPort) {
        InetSocketAddress addr = new InetSocketAddress(agentHost, agentPort);
        return new Proxy(Proxy.Type.HTTP, addr);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        log.info("获取客户端ip: " + ip);
        return ip;
    }

    public static Map<Object, Object> getHeaders(HttpServletRequest request) {
        Map<Object, Object> headers = new HashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    headers.put(name, value);
                }
            }
        }
        return headers;
    }

}
