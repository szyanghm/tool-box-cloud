package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * job任务表
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_quartz_job")
@Tag(name = "QuartzJob对象", description = "定时任务表")
public class QuartzJob extends BaseModel<QuartzJob> implements Serializable {

    /**
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;
    /**
     * 定时任务固定执行类名
     */
    @Schema(description = "定时任务固定执行类名")
    private String jobGroup;
    /**
     * job的参数
     */
    @Schema(description = "任务参数")
    private String parameter;
    /**
     * job描述信息
     */
    @Schema(description = "任务名称描述")
    private String description;
    /**
     * 方法名
     */
    @Schema(description = "任务方法名")
    private String methodName;
    /**
     * job的jar路径
     */
    @Schema(description = "任务类名")
    private String classPath;
}
