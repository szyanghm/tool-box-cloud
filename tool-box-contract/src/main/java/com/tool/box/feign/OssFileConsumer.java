package com.tool.box.feign;

import com.tool.box.common.CommonKV;
import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.vo.OssFileVO;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 上传附件到文件服务
     *
     * @param file 附件
     * @return 上传结果
     */
    @RequestMapping(value = "/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVO<OssFileVO> upload(@RequestPart MultipartFile file);

    /**
     * 批量上传附件到文件服务
     *
     * @param files 附件集合
     * @return 上传结果
     */
    @RequestMapping(value = "/uploads", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResultVO<List<OssFileVO>> uploads(@RequestPart MultipartFile[] files);

    /**
     * 根据fileKey删除附件
     *
     * @param fileKy 附件fileKey唯一
     * @return 删除结果
     */
    @RequestMapping(value = "/delete")
    ResultVO<?> delete(@RequestParam("fileKy") String fileKy);
    /**
     * 根据fileKey获取下载路径
     *
     * @param fileKy 附件fileKey唯一
     * @return 下载路径
     */
    @RequestMapping(value = "/getUrl")
    ResultVO getUrl(@RequestParam("fileKy") String fileKy);
    /**
     * 批量根据fileKey获取下载路径
     *
     * @param fileKys 附件fileKey唯一
     * @return 下载路径
     */
    @RequestMapping(value = "/getUrls")
    ResultVO<List<CommonKV>> getUrls(@RequestParam("fileKys") List<String> fileKys);
}
