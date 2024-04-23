package com.tool.box.controller;

import com.tool.box.common.CommonKV;
import com.tool.box.exception.InternalApiException;
import com.tool.box.minio.MinioUtils;
import com.tool.box.vo.OssFileVO;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/uploads", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO<List<OssFileVO>> uploads(@RequestPart MultipartFile[] files) {
        try {
            List<OssFileVO> list = new ArrayList<>();
            for (MultipartFile file : files) {
                OssFileVO vo = minioUtils.upLoadFile("system", String.valueOf(System.currentTimeMillis()), file);
                list.add(vo);
            }
            return ResultVO.success(list);
        } catch (InternalApiException e) {
            return new ResultVO<>(e.getCode(), e.getMessage());
        }
    }

    /**
     * 删除附件
     *
     * @param fileKy 附件key唯一
     * @return 删除结果
     */
    @RequestMapping(value = "/delete")
    public ResultVO<?> delete(@RequestParam("fileKy") String fileKy) {
        try {
            return ResultVO.success(minioUtils.removeFile(fileKy));
        } catch (InternalApiException e) {
            return new ResultVO<>(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/getUrl")
    public ResultVO<String> getUrl(@RequestParam("fileKy") String fileKy) {
        String url = minioUtils.getUrl("mini-tool", fileKy);
        return ResultVO.success(url);
    }

    @RequestMapping(value = "/getUrls")
    public ResultVO<List<CommonKV>> getUrls(@RequestParam("filePaths") List<String> fileKys) {
        List<CommonKV> list = new ArrayList<>();
        for (String fileKy : fileKys) {
            String url = minioUtils.getUrl("mini-tool", fileKy);
            CommonKV kv = CommonKV.builder().key(fileKy).val(url).build();
            list.add(kv);
        }
        return ResultVO.success(list);
    }
}
