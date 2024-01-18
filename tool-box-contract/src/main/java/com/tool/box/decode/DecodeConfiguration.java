package com.tool.box.decode;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.vo.ResultVO;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 错误解码器
 *
 * @Author v_haimiyang
 * @Date 2023/8/14 18:26
 * @Version 1.0
 */
@Slf4j
public class DecodeConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    @Bean
    public Decoder decoder() {
        return new FeignResultDecoder();
    }

    public static class FeignResultDecoder implements Decoder {
        @Override
        public Object decode(Response response, Type type) {
            try {
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                ResultVO resultVO = JSONObject.parseObject(bodyStr, ResultVO.class);
                if (!Objects.equals(SystemCodeEnum.OK.getCode(), resultVO.getCode())) {
                    log.info(" 【===feign远程调用Server Exception===】,{}", resultVO.getMsg());
                    throw new InternalApiException(resultVO.getCode(), resultVO.getMsg());
                }
                log.info("脱壳后的结果:" + JSONObject.toJSONString(resultVO.getData()));
                if (ObjectUtil.isEmpty(resultVO.getData())) {
                    return "处理成功";
                }
                return JSONObject.parseObject(JSONObject.toJSONString(resultVO.getData()), type);
            } catch (Exception e) {
                throw new InternalApiException(SystemCodeEnum.FEIGN_DECODE_ERROR);
            }
        }
    }

    /**
     * 自定义错误解码器
     */
    public static class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            if (response.status() != HttpStatus.OK.value()) {
                try {
                    String json = Util.toString(response.body().asReader());
                    InternalApiException remoteBaseException = JSONUtil.toBean(json, InternalApiException.class);
                    log.info(" 【===feign远程调用Server Exception===】,{}", exception.getMessage());
                    exception = remoteBaseException;
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    exception = new InternalApiException(response.status(), e.getMessage());
                }
            }
            return exception;
        }
    }

}
