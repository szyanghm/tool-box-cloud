package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskConfigDTO;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.model.TaskConfig;
import com.tool.box.vo.ResultVO;

import java.util.List;

/**
 * 定时任务执行表 服务类
 *
 * @author v_haimiyang
 * @since 2024-01-08
 */
public interface ITaskConfigService extends IService<TaskConfig> {

    /**
     * 新增任务
     *
     * @return 新增结果
     */
    ResultVO<String> add(TaskConfigDTO dto);

    /**
     * 更新执行任务消息
     *
     * @return 新增结果
     */
    ResultVO<?> updateTaskConfig(TaskMsgDTO dto);

    /**
     * 按任务状态查询执行的任务
     *
     * @param states 任务状态
     * @return 任务列表
     */
    List<QuartzJobTask> findJobTaskList(List<String> states);

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
     * @param id 主键id
     * @return 任务列表
     */
    QuartzJobTask getJobTask(String id);
}
