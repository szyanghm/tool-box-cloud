package com.tool.box.controller;

import com.tool.box.minio.MinioUtils;
import com.tool.box.vo.OssFileVO;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 附件操作-控制器
 *
 * @Author v_haimiyang
 * @Date 2024/3/22 15:32
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/oss")
public class OssFileController {

    @Resource
    private MinioUtils minioUtils;

    @PostMapping(value = "/created")
    public ResultVO<?> send() {
        minioUtils.makeBucket("mini-tool");
        return ResultVO.success();
    }

    @RequestMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO mediaImgUpload(@RequestPart MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        OssFileVO vo = minioUtils.upLoadFile("test", String.valueOf(System.currentTimeMillis()), file);
        return ResultVO.success(vo);
    }

    @RequestMapping(value = "/uploads", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO mediaImgUpload(@RequestPart MultipartFile[] files) {
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return ResultVO.success();
    }

    @RequestMapping(value = "/getUrl")
    public ResultVO getUrl(@RequestParam("fileName") String fileName) {
        String url = minioUtils.getUrl("mini-tool", fileName, 50000);
        return ResultVO.success(url);
    }
}
