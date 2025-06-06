package com.tool.box.decode;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.exception.InternalApiException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * feign错误解码器
 *
 * @Author v_haimiyang
 * @Date 2024/2/4 11:35
 * @Version 1.0
 */
@Slf4j
public class NotBreakerConfiguration {

    @Bean
    public ErrorDecoder resultErrorDecoder() {
        return new ResultErrorDecoder();
    }

    /**
     * 自定义错误解码器
     */
    public static class ResultErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            if (response.status() != HttpStatus.OK.value()) {
                try {
                    String json = Util.toString(response.body().asReader());
                    InternalApiException apiException = JSONObject.parseObject(json, InternalApiException.class);
                    exception = apiException;
                    log.info("【===feign远程调用Server Exception===】,{}", exception.getMessage());
                } catch (IOException e) {
                    exception = new InternalApiException(response.status(), e.getMessage());
                }
            }
            return exception;
        }




    }
}
