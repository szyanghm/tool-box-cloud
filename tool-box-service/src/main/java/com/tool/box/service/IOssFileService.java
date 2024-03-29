package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.model.OssFile;
import com.tool.box.vo.OssFileVO;


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
     * @param vo 附件信息
     */
    void updateFile(OssFileVO vo);

    /**
     * 删除文件服务附件
     *
     * @param filePath 附件路径
     */
    void deleteOssFile(String filePath);
}
