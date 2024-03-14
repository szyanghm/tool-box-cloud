package com.tool.box.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author v_haimiyang
 * @Date 2024/1/19 10:16
 * @Version 1.0
 */
@Configuration
public class TaskConfiguration {

    /**
     * 定时任务开关（true使用定时任务/false不使用定时任务）
     */
    @Value("${task.enabled}")
    public Boolean taskEnabled;

    /**
     * 服务名
     */
    @Value("${spring.application.name}")
    public String applicationName;

}
