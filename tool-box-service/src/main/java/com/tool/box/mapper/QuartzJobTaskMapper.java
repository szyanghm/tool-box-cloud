package com.tool.box.mapper;

import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskMsgDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/1/12 18:18
 * @Version 1.0
 */
@Repository
public interface QuartzJobTaskMapper {

    /**
     * 按任务状态查询执行的任务
     *
     * @param groupName 按服务分组名
     * @return 任务列表
     */
    List<QuartzJobTask> findJobTaskList(String groupName);

    /**
     * 更新执行任务消息
     *
     * @return 更增结果
     */
    int updateTaskConfig(@Param("dto") TaskMsgDTO dto);
}
