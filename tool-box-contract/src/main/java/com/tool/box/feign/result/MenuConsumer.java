package com.tool.box.feign.result;

import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.dto.MenuDTO;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * tool-box-service服务：feign客户端(不脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/8/14 18:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "notBreaker-validation"
        , path = "/menu", configuration = NotBreakerConfiguration.class)
public interface MenuConsumer {

    @PostMapping(value = "/list")
    ResultVO<?> getMenu(@RequestBody MenuDTO dto);

}
