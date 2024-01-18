package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.model.QuartzJob;
import org.springframework.stereotype.Repository;

/**
 * 定时任务表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2023-12-29
 */
@Repository
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

}
