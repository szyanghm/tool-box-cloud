package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.model.OssFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 附件表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2024-03-27
 */
@Repository
public interface OssFileMapper extends BaseMapper<OssFile> {

    /**
     * 根据fileKey删除附件
     *
     * @param fileKeys fileKey集合
     */
    void deleteByFileKey(@Param("list") List<String> fileKeys);
}
