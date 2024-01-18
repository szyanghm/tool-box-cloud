package com.tool.box.common;

import lombok.Data;

import java.util.Date;

/**
 * job任务
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Data
public class QuartzJobTask {

    /**
     * t_task_config表主键唯一id
     */
    private String taskId;

    /**
     * job分组名称（默认服务名称）
     */
    private String groupName;
    /**
     * job的执行类
     */
    private String jobGroup;
    /**
     * 执行时间-cron表达式（和执行次数二选一）
     */
    private String cron;
    /**
     * 执行次数（和执行时间二选一）
     */
    private int repeatCount;
    /**
     * 执行开始时间
     */
    private Date startDate;
    /**
     * 执行结束时间
     */
    private Date endDate;
    /**
     * job的执行参数
     */
    private String parameter;
    /**
     * 任务类型（repeat:循环按时间执行/not_repeat:非循环按次数执行）
     */
    private String type;
    /**
     * 间隔时间
     */
    private int intervalTime;
    /**
     * 间隔时间单位（H:小时，M:分钟，S:秒）
     */
    private String intervalTimeUnit;
    /**
     * job默认描述
     */
    private String description;
    /**
     * job任务执行结果消息
     */
    private String taskMsg;
    /**
     * job的jar路径
     */
    private String classPath;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * job的执行状态(R:开启,S:暂停,P:关闭)
     */
    private String state;
}
