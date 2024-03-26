package com.tool.box.controller;

import com.tool.box.feign.OssFileConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author v_haimiyang
 * @Date 2024/3/25 15:21
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/oss")
public class OssFileController {

    @Resource
    private OssFileConsumer ossFileConsumer;

    @RequestMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO upload(@RequestPart MultipartFile file) {
        return ossFileConsumer.upload(file);
    }

    @RequestMapping(value = "/uploads", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO uploads(@RequestPart MultipartFile[] file) {
        return ossFileConsumer.uploads(file);
    }

    @RequestMapping(value = "/getUrl")
    public ResultVO getUrl(@RequestParam("fileName") String fileName) {
        return ossFileConsumer.getUrl(fileName);
    }
}
