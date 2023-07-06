package com.tool.box.decode;

import com.alibaba.fastjson2.JSONObject;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.vo.ResultVO;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 解码器
 *
 * @Author v_haimiyang
 * @Date 2023/6/28 15:18
 * @Version 1.0
 */
public class NotBreakerConfiguration {

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
                    throw new InternalApiException(resultVO.getCode(), resultVO.getMsg());
                }
                return JSONObject.parseObject(JSONObject.toJSONString(resultVO.getData()), type);
            } catch (Exception e) {
                throw new InternalApiException(SystemCodeEnum.FEIGN_DECODE_ERROR);
            }
        }
    }
}
