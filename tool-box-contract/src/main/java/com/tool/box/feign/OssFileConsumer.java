package com.tool.box.feign;

import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * tool-box-service服务：feign客户端(自动脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/5/24 17:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-file", contextId = "oss-validation", path = "/file/oss"
        , configuration = NotBreakerConfiguration.class)
public interface OssFileConsumer {

    //上传文件
    @RequestMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVO upload(@RequestPart MultipartFile file);

    @RequestMapping(value = "/uploads", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVO uploads(@RequestPart MultipartFile[] files);

    @RequestMapping(value = "/getUrl")
    ResultVO getUrl(@RequestParam("fileName") String fileName);
}
