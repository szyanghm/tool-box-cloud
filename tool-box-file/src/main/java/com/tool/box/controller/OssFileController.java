package com.tool.box.controller;

import com.tool.box.exception.InternalApiException;
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
    public ResultVO<OssFileVO> upload(@RequestPart MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        try {
            OssFileVO vo = minioUtils.upLoadFile("system", String.valueOf(System.currentTimeMillis()), file);
            return ResultVO.success(vo);
        } catch (InternalApiException e) {
            return new ResultVO<>(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除附件
     *
     * @param filePath 附件路径
     * @return 删除结果
     */
    @RequestMapping(value = "/delete")
    public ResultVO<?> delete(@RequestParam("filePath") String filePath) {
        try {
            return ResultVO.success(minioUtils.removeFile(filePath));
        } catch (InternalApiException e) {
            return new ResultVO<>(e.getCode(), e.getMessage());
        }
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
    public ResultVO<String> getUrl(@RequestParam("fileName") String fileName) {
        String url = minioUtils.getUrl("mini-tool", fileName);
        return ResultVO.success(url);
    }
}
