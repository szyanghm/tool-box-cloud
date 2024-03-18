package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.model.TaskConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务执行表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2024-01-08
 */
@Repository
public interface TaskConfigMapper extends BaseMapper<TaskConfig> {

    /**
     * 按任务状态查询执行的任务
     *
     * @param states 任务状态
     * @return 任务列表
     */
    List<QuartzJobTask> findJobTaskList(@Param("states") List<String> states);

    /**
     * 按任务状态查询执行的任务
     *
     * @param id 根据jobId查询任务
     * @return 任务列表
     */
    List<QuartzJobTask> findList(String id);

    /**
     * 按任务状态查询执行的任务
     *
     * @param id 主键
     * @return 任务列表
     */
    QuartzJobTask getJobTask(String id);
}
