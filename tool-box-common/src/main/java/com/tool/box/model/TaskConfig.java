package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务执行表
 * </p>
 *
 * @author v_haimiyang
 * @since 2024-01-08
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_task_config")
@Tag(name = "TaskConfig对象", description = "定时任务执行表")
public class TaskConfig extends BaseModel<TaskConfig> implements Serializable {

    /**
     * 关联t_quartz_job表主键id
     */
    @Schema(description = "关联t_quartz_job表主键id")
    private String jobId;
    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String taskName;
    /**
     * 执行时间-cron表达式（和执行次数二选一）
     */
    @Schema(description = "执行时间-cron表达式（和执行次数二选一）")
    private String cron;
    /**
     * 执行次数（和执行时间二选一）
     */
    @Schema(description = "执行次数（和执行时间二选一）")
    private int repeatCount;
    /**
     * 执行开始时间
     */
    @Schema(description = "执行开始时间")
    private Date startDate;
    /**
     * 执行结束时间
     */
    @Schema(description = "执行结束时间")
    private Date endDate;
    /**
     * 任务执行参数
     */
    @Schema(description = "任务执行参数")
    private String parameter;
    /**
     * 任务类型（repeat:循环按时间执行/not_repeat:非循环按次数执行）
     */
    @Schema(description = "任务类型（repeat:循环按时间执行/not_repeat:非循环按次数执行）")
    private String type;
    /**
     * 间隔时间
     */
    @Schema(description = "间隔时间")
    private int intervalTime;
    /**
     * 间隔时间单位（H:小时，M:分钟，S:秒）
     */
    @Schema(description = "间隔时间单位（H:小时，M:分钟，S:秒）")
    private String intervalTimeUnit;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;
    /**
     * 任务执行结果消息
     */
    @Schema(description = "任务执行结果消息")
    private String taskMsg;
    /**
     * 任务状态(R: 开启,S:暂停,P:关闭)
     */
    @Schema(description = "任务状态(R: 开启,S:暂停,P:关闭)")
    private String state;

}
