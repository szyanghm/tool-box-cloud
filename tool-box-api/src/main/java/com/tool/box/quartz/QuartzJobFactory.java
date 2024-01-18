package com.tool.box.quartz;

import com.tool.box.service.QuartzJobTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

import javax.annotation.Resource;

/**
 * @author v_haimiyang
 * @date 2021/12/30 11:37
 */

@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobFactory implements Job {

    @Resource
    private QuartzJobTaskService quartzJobTaskService;

    @Override
    public void execute(JobExecutionContext context) {
        quartzJobTaskService.execute(context);
    }
}
