package com.tool.box.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.common.Contents;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.mapper.OssFileMapper;
import com.tool.box.model.OssFile;
import com.tool.box.service.IOssFileService;
import com.tool.box.vo.OssFileVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 附件表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-03-27
 */
@Service
public class OssFileServiceImpl extends ServiceImpl<OssFileMapper, OssFile> implements IOssFileService {

    @Resource
    private OssFileConsumer ossFileConsumer;

    @Transactional(rollbackFor = Exception.class)
    public void updateFile(OssFileVO vo) {
        OssFile ossFile = baseMapper.selectOne(new UpdateWrapper<OssFile>().lambda()
                .eq(OssFile::getFileKey, vo.getFileKey())
        );
        String filePath = "";
        if (ossFile != null) {
            filePath = ossFile.getFilePath();
            ossFile.setFileKey(vo.getFileKey());
            ossFile.setFilePath(vo.getFilePath());
            ossFile.setFileName(vo.getFileName());
            ossFile.setDomain(vo.getDomain());
            ossFile.setFileSize(vo.getFileSize());
            ossFile.setContentType(vo.getContentType());
            ossFile.setOriginalName(vo.getOriginalName());
            ossFile.setHash(vo.getHash());
            ossFile.setFileType(Contents.AVATAR_FILE);
            baseMapper.updateById(ossFile);
        } else {
            ossFile = new OssFile();
            BeanUtils.copyProperties(vo, ossFile);
            ossFile.setFileType(Contents.AVATAR_FILE);
            baseMapper.insert(ossFile);
        }
        //删除原附件
        if (StringUtils.isNotBlank(filePath)) {
            deleteOssFile(filePath);
        }
    }

    @Async
    @Override
    public void deleteOssFile(String filePath) {
        ossFileConsumer.delete(filePath);
    }
}
