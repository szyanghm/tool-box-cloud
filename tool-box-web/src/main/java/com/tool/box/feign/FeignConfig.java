package com.tool.box.feign;

import com.tool.box.common.Contents;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            String token= request.getHeader(Contents.TOKEN);
            if (StringUtils.isNotBlank(token)) {
                requestTemplate.header(Contents.TOKEN, token);
            }
        };
    }
}
