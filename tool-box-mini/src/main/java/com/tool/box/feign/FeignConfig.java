package com.tool.box.feign;

import com.tool.box.common.Contents;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author v_haimiyang
 * @Date 2023/7/26 11:51
 * @Version 1.0
 */
@Component
public class FeignConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(Contents.X_ACCESS_TOKEN);
            if (StringUtils.isNotBlank(token)) {
                requestTemplate.header(Contents.X_ACCESS_TOKEN, token);
            }
        };
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Logger.Level feignLogLevel() {
        /**
         *   日志级别：
         *   NONE（不记录日志 (默认)）
         *   BASIC（只记录请求方法和URL以及响应状态代码和执行时间）
         *   HEADERS（记录请求和应答的头的基本信息）
         *   FULL（记录请求和响应的头信息，正文和元数据）
         */
        return Logger.Level.FULL;
    }

}
