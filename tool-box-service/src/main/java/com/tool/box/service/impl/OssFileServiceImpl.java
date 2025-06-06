package com.tool.box.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.bo.OssFileBO;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.mapper.OssFileMapper;
import com.tool.box.model.OssFile;
import com.tool.box.service.IOssFileService;
import com.tool.box.utils.SystemUtils;
import com.tool.box.vo.OssFileVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public void updateFile(OssFileVO vo, String backFileKey) {
        OssFile ossFile = SystemUtils.getOssFile(vo);
        baseMapper.insert(ossFile);
        //删除原附件
        if (StringUtils.isNotBlank(backFileKey)) {
            this.remove(new QueryWrapper<OssFile>().lambda().eq(OssFile::getFileKey, backFileKey));
            deleteOssFile(backFileKey);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateFiles(OssFileBO bo) {
        List<OssFile> ossFileList = new ArrayList<>();
        for (OssFileVO vo : bo.getList()) {
            OssFile ossFile = SystemUtils.getOssFile(vo);
            ossFileList.add(ossFile);
        }
        this.saveBatch(ossFileList);
        //删除原附件
        if (CollectionUtil.isNotEmpty(bo.getBackFileKeys())) {
            deleteByFileKey(bo.getBackFileKeys());
            for (String fileKey : bo.getBackFileKeys()) {
                deleteOssFile(fileKey);
            }
        }
    }

    @Async
    @Override
    public void deleteByFileKey(List<String> fileKeys) {
        baseMapper.deleteByFileKey(fileKeys);
    }

    @Async
    @Override
    public void deleteOssFile(String fileKey) {
        ossFileConsumer.delete(fileKey);
    }
}
