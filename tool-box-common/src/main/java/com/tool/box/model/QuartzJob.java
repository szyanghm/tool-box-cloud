package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "QuartzJob对象", description = "定时任务表")
public class QuartzJob extends BaseModel<QuartzJob> implements Serializable {

    /**
     * 分组名称
     */
    @ApiModelProperty("分组名称")
    private String groupName;
    /**
     * 定时任务固定执行类名
     */
    @ApiModelProperty("定时任务固定执行类名")
    private String jobGroup;
    /**
     * job的参数
     */
    @ApiModelProperty("任务参数")
    private String parameter;
    /**
     * job描述信息
     */
    @ApiModelProperty("任务名称描述")
    private String description;
    /**
     * 方法名
     */
    @ApiModelProperty("任务方法名")
    private String methodName;
    /**
     * job的jar路径
     */
    @ApiModelProperty("任务类名")
    private String classPath;
}
