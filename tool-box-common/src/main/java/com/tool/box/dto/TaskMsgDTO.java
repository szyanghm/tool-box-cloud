package com.tool.box.dto;

import lombok.Data;

/**
 * 更新任务消息
 *
 * @Author v_haimiyang
 * @Date 2024/1/11 10:08
 * @Version 1.0
 */
@Data
public class TaskMsgDTO {

    /**
     * task_config主键id
     */
    private String id;
    /**
     * task_config任务消息
     */
    private String taskMsg;
}
