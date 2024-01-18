package com.tool.box.service;

import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.vo.ResultVO;

import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/1/12 18:20
 * @Version 1.0
 */
public interface IQuartzJobTaskService {


    /**
     * 按任务状态查询执行的任务
     *
     * @param groupName 按服务分组名
     * @return 任务列表
     */
    List<QuartzJobTask> findJobTaskList(String groupName);

    /**
     * 更新执行任务消息
     */
    ResultVO<?> updateTaskConfig(TaskMsgDTO dto);
}
