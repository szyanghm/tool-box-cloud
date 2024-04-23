package com.tool.box.service;

import com.tool.box.common.QuartzJobTask;
import com.tool.box.config.TaskConfiguration;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.feign.QuartzJobTaskConsumer;
import com.tool.box.model.TaskConfig;
import com.tool.box.quartz.QuartzJobUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/3/13 18:14
 * @Version 1.0
 */
@Slf4j
@Component
public class QuartzJobTaskService {

    @Resource
    private QuartzJobUtils quartzJobUtils;
    @Resource
    private QuartzJobTaskConsumer quartzJobTaskConsumer;
    @Resource
    private TaskConfiguration configuration;

    /**
     * 初始化启动所有的Job
     */
    @PostConstruct
    public void initialize() {
        refreshAllJobs();
    }

    /**
     * 重启状态为：R、S的所有定时任务
     */
    public void refreshAllJobs() {
        try {
            String applicationName = configuration.applicationName;
            if (!configuration.taskEnabled) {
                quartzJobUtils.schedulerDeleteAll(applicationName);
                log.error(applicationName + SystemCodeEnum.TASK_NOT_ENABLED.getMessage());
                return;
            }
            List<QuartzJobTask> list = quartzJobTaskConsumer.findJobTaskList(applicationName);
            quartzJobUtils.refreshAllJobs(list);
        } catch (Exception e) {
            log.error(SystemCodeEnum.TASK_FEIGN_SERVICE_FAIL.getMessage());
        }
    }

    /**
     * 定时任务核心
     *
     * @param context quartz上下文信息
     */
    public void execute(JobExecutionContext context) {
        TaskConfig taskConfig = QuartzJobUtils.execute(context);
        if (taskConfig.getId() != null) {
            TaskMsgDTO dto = new TaskMsgDTO();
            dto.setId(String.valueOf(taskConfig.getId()));
            dto.setTaskMsg(taskConfig.getTaskMsg());
            try {
                quartzJobTaskConsumer.updateTaskConfig(dto);
            } catch (Exception e) {
                log.error(SystemCodeEnum.TASK_FEIGN_SERVICE_FAIL.getMessage());
            }
        }
    }
}
