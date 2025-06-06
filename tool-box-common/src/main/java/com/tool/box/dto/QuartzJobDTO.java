package com.tool.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.Map;

/**
 * job任务表
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Data
@Tag(name = "QuartzJobDTO对象", description = "新增定时任务模块")
public class QuartzJobDTO {

    /**
     * 主键唯一id(修改时，必填)
     */
    @Schema(description = "主键唯一id")
    private String id;

    /**
     * job分组名称（默认为：服务名）
     */
    @Schema(description = "分组名称")
    private String groupName;
    /**
     * job执行类
     */
    @Schema(description = "定时任务固定执行类名")
    private String jobGroup;
    /**
     * job的参数
     */
    @Schema(description = "job的参数")
    private Map<String, Object> parameter;
    /**
     * job描述信息
     */
    @Schema(description = "job描述信息")
    private String description;
    /**
     * 方法名
     */
    @Schema(description = "方法名")
    private String methodName;
    /**
     * job的jar路径
     */
    @Schema(description = "job的jar路径")
    private String classPath;
}
