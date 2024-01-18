package com.tool.box.service;

import com.tool.box.common.QuartzJobTask;
import com.tool.box.config.SystemConfig;
import com.tool.box.dto.TaskConfigDTO;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.enums.CommonEnum;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.feign.QuartzJobTaskConsumer;
import com.tool.box.model.TaskConfig;
import com.tool.box.quartz.QuartzJobUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/1/8 9:42
 * @Version 1.0
 */
@Slf4j
@Component
public class QuartzJobTaskService {

    @Resource
    private ITaskConfigService taskConfigService;
    @Resource
    private QuartzJobUtils quartzJobUtils;
    @Resource
    private QuartzJobTaskConsumer quartzJobTaskConsumer;
    @Resource
    private SystemConfig systemConfig;

    /**
     * 初始化启动所有的Job
     */
    @PostConstruct
    public void initialize() {
        refreshAllJobs();
    }

    /**
     * 新增定时任务
     *
     * @param dto 入参
     * @return 新增结果
     */
    public ResultVO<?> add(TaskConfigDTO dto) {

        if (CommonEnum.REPEAT.getValue().equals(dto.getType())) {
            if (StringUtils.isBlank(dto.getCron())) {
                String cron = QuartzJobUtils.createCronExpression(dto);
                dto.setCron(cron);
            }
        }
        ResultVO<String> resultVO = taskConfigService.add(dto);
        if (CommonEnum.R.getValue().equals(dto.getState()) && resultVO.isSuccess()) {
            QuartzJobTask quartzJobTask = taskConfigService.getJobTask(resultVO.getData());
            //启动定时任务
            quartzJobUtils.execute(quartzJobTask);
        }
        return resultVO;
    }

    /**
     * 重启状态为：S的所有定时任务
     */
    public void refreshJobs() {
        List<QuartzJobTask> list = taskConfigService.findJobTaskList(Collections
                .singletonList(CommonEnum.S.getValue()));
        quartzJobUtils.refreshJobs(list);
    }

    /**
     * 重启状态为：R、S的所有定时任务
     */
    public void refreshAllJobs() {
        try {
            List<QuartzJobTask> list = quartzJobTaskConsumer.findJobTaskList(systemConfig.applicationName);
            quartzJobUtils.refreshAllJobs(list);
        } catch (Exception e) {
            log.error(SystemCodeEnum.TASK_FEIGN_SERVICE_FAIL.getMsg());
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
            quartzJobTaskConsumer.updateTaskConfig(dto);
        }
    }
}
