package com.tool.box.feign.result;

import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.dto.GoodsInfoDTO;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * tool-box-service服务：feign客户端(不脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/8/14 18:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "notBreaker-validation"
        , path = "/goodsInfo", configuration = NotBreakerConfiguration.class)
public interface GoodsInfoConsumer {

    @RequestMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVO add(@RequestPart("files") MultipartFile[] files, @RequestParam("goodsInfo") String goodsInfo);
}
