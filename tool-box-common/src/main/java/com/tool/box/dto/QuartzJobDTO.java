package com.tool.box.dto;

import lombok.Data;

/**
 * job任务表
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Data
public class QuartzJobDTO {

    /**
     * job分组名称（默认为：服务名）
     */
    private String groupName;
    /**
     * job执行类
     */
    private String jobGroup;
    /**
     * job的参数
     */
    private String parameter;
    /**
     * job描述信息
     */
    private String description;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * job的jar路径
     */
    private String classPath;
}
