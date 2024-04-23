package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.bo.OssFileBO;
import com.tool.box.model.OssFile;
import com.tool.box.vo.OssFileVO;

import java.util.List;


/**
 * 附件表 服务类
 *
 * @author v_haimiyang
 * @since 2024-03-27
 */
public interface IOssFileService extends IService<OssFile> {

    /**
     * 更新附件表
     *
     * @param vo          附件信息
     * @param backFileKey 旧fileKey
     */
    void updateFile(OssFileVO vo, String backFileKey);

    /**
     * 批量保存附件信息
     *
     * @param bo 入参
     */
    void updateFiles(OssFileBO bo);

    /**
     * 根据fileKey删除附件
     *
     * @param fileKeys fileKey集合
     */
    void deleteByFileKey(List<String> fileKeys);
    /**
     * 删除文件服务附件
     *
     * @param filePath 附件路径
     */
    void deleteOssFile(String filePath);
}
