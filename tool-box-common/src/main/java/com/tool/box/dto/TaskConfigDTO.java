package com.tool.box.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tool.box.common.validata.EnumValue;
import com.tool.box.utils.DateUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Map;

/**
 * 定时任务执行表—DTO
 *
 * @Author v_haimiyang
 * @Date 2024/1/10 11:14
 * @Version 1.0
 */
@Data
public class TaskConfigDTO {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 关联t_quartz_job表主键id
     */
    @NotBlank(message = "jobId不允许为空!")
    private String jobId;

    /**
     * 定时任务间隔时间单位
     */
    @EnumValue(strValues = {"YEAR", "MONTH", "DAY", "WEEK", "H", "MIN", "S"}, isRequire = true)
    private String intervalTimeUnit;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 间隔时间
     */
    private int intervalTime;
    /**
     * 任务执行参数
     */
    private Map<String, Object> parameter;
    /**
     * 任务执行开始时间（时，分，秒）
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.DEFAULT_DATE_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_PATTERN)
    private Date startDate;
    /**
     * 任务执行结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = DateUtils.DEFAULT_DATE_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_PATTERN)
    private Date endDate;
    /**
     * 执行次数（和执行时间二选一）
     */
    private int repeatCount;
    /**
     * 每周（7天）
     */
    private Integer[] dayOfWeeks = new Integer[7];
    /**
     * 每月（值：32，表示每月最后一天）
     */
    private Integer[] dayOfMonths = new Integer[31];
    /**
     * 每年（12个月）
     */
    private Integer[] months = new Integer[12];
    /**
     * 任务类型（repeat:循环按时间执行/not_repeat:非循环按次数执行）
     */
    @EnumValue(strValues = {"repeat", "not_repeat"}, isRequire = true)
    private String type;
    /**
     * 任务状态(R: 开启,S:暂停,P:关闭)
     */
    @EnumValue(strValues = {"R", "S", "P"}, isRequire = true)
    private String state;
    /**
     * 任务备注
     */
    private String remarks;

}
