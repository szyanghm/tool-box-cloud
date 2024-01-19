package com.tool.box.quartz;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tool.box.common.Contents;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.config.TaskConfiguration;
import com.tool.box.dto.BaseTaskHeadDTO;
import com.tool.box.dto.TaskConfigDTO;
import com.tool.box.enums.*;
import com.tool.box.exception.InternalApiException;
import com.tool.box.model.TaskConfig;
import com.tool.box.utils.ApplicationContextUtils;
import com.tool.box.utils.SystemUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author v_haimiyang
 * @date 2021/12/30 11:37
 */

@Slf4j
@Component
public class QuartzJobUtils {

    @Resource
    private Scheduler scheduler;
    @Resource
    private TaskConfiguration configuration;

    /**
     * Quartz任务核心方法
     *
     * @param quartzJob 任务主体对象
     */
    public void execute(QuartzJobTask quartzJob) {
        synchronized (log) {
            try {
                JobKey jobKey = getJobKey(quartzJob);
                JobDataMap map = getJobDataMap(quartzJob);
                JobDetail jobDetail = getJobDetail(jobKey, quartzJob.getDescription(), map);
                scheduler.pauseJob(jobKey);
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
                // 将trigger和 jobDetail 加入这个调度
                scheduler.scheduleJob(jobDetail, getTrigger(quartzJob));
                // 启动scheduler
                //scheduler.start();
            } catch (Exception e) {
                throw new InternalApiException(SystemCodeEnum.TASK_START_EXCEPTION);
            }
        }
    }

    /**
     * 重新启动所有的job
     *
     * @param list 任务job集合
     */
    public void refreshAllJobs(List<QuartzJobTask> list) {
        if (CollectionUtil.isEmpty(list)) {
            log.info("暂无可需要重启的定时任务");
            return;
        }
        schedulerDeleteAll(configuration.applicationName);
        for (QuartzJobTask job : list) {
            execute(job);
        }
    }

    /**
     * 动态刷新启动任务(状态为:S)
     *
     * @param list 任务job集合
     */
    public void refreshJobs(List<QuartzJobTask> list) {
        if (CollectionUtil.isEmpty(list)) {
            log.info("暂无可需要刷新的定时任务");
            return;
        }
        synchronized (log) {
            //只允许一个线程进入操作
            for (QuartzJobTask job : list) {
                execute(job);
            }
        }
    }

    public static JobKey getJobKey(QuartzJobTask job) {
        return JobKey.jobKey(SystemUtils.getGroupName(job), job.getJobGroup());
    }


    /**
     * 从数据库中注册的所有JOB
     *
     * @param job 任务对象
     * @return 需要注册的JobDataMap
     */
    public static JobDataMap getJobDataMap(QuartzJobTask job) {
        JobDataMap map = new JobDataMap();
        map.put("taskId", job.getTaskId());
        map.put("groupName", job.getGroupName());
        map.put("jobGroup", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("taskMsg", job.getTaskMsg());
        map.put("methodName", job.getMethodName());
        map.put("classPath", job.getClassPath());
        map.put("state", job.getState());
        return map;
    }

    /**
     * 进行注册的所有JOB
     *
     * @param jobKey      任务唯一key
     * @param description 任务描述
     * @param map         任务对象
     * @return 注册后的JobDetail
     */
    public static JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) {
        String className = StrUtil.concat(true, "com.tool.box.quartz."
                , String.valueOf(map.get("jobGroup")));
        log.info("执行业务job类:{}", className);
        Class clz = null;
        try {
            clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return JobBuilder.newJob(clz)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    /**
     * 获取Trigger (Job的触发器,执行规则)
     *
     * @param job 任务对象信息
     * @return Job的触发器, 执行规则
     */
    public static Trigger getTrigger(QuartzJobTask job) {
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(job.getGroupName() + job.getTaskId(), job.getJobGroup());
        if (job.getStartDate() != null) {
            //任务执行开始时间
            triggerBuilder.startAt(job.getStartDate());
        }
        //当结束时间不为空，结束时间必须大于当前时间
        if (job.getEndDate() != null && job.getEndDate().getTime() > System.currentTimeMillis()) {
            //任务执行结束时间
            triggerBuilder.endAt(job.getEndDate());
        }
        if (CommonEnum.REPEAT.getValue().equals(job.getType())) {
            //按cron表达式执行
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()));
        } else {
            //按重复次数执行
            triggerBuilder.withSchedule(getSimpleScheduleBuilder(job)
                    .withRepeatCount(job.getRepeatCount() - Contents.NUM_1));
        }
        return triggerBuilder.build();
    }

    /**
     * 时间定时任务执行的间隔时间
     *
     * @param job 定时任务对象
     * @return SimpleScheduleBuilder间隔对象
     */
    public static SimpleScheduleBuilder getSimpleScheduleBuilder(QuartzJobTask job) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        if (TimeUnitEnum.H.getDesc().equals(job.getIntervalTimeUnit())) {
            //间隔时间：小时
            scheduleBuilder.withIntervalInHours(job.getIntervalTime());
        }
        if (TimeUnitEnum.M.getDesc().equals(job.getIntervalTimeUnit())) {
            //间隔时间：分钟
            scheduleBuilder.withIntervalInMinutes(job.getIntervalTime());
        }
        if (TimeUnitEnum.S.getDesc().equals(job.getIntervalTimeUnit())) {
            //间隔时间：秒
            scheduleBuilder.withIntervalInSeconds(job.getIntervalTime());
        }
        return scheduleBuilder;
    }

    /**
     * 获取定时任务：类型，方法，方法参数
     *
     * @param map quartz的任务Map对象
     * @return 定时任务：类型，方法，方法参数
     */
    public static BaseTaskHeadDTO findMethod(JobDataMap map) {
        BaseTaskHeadDTO dto = new BaseTaskHeadDTO();
        // 获取到该类
        try {
            Class<?> clazz = Class.forName(String.valueOf(map.get("classPath")));
            Object bean = ApplicationContextUtils.getBean(clazz);
            Class<?> beanClazz = bean.getClass();
            Method[] declaredMethods = beanClazz.getDeclaredMethods();
            Method targetMethod = null;
            // 获取到目标方法
            for (Method declaredMethod : declaredMethods) {
                String name = declaredMethod.getName();
                if (String.valueOf(map.get("methodName")).equalsIgnoreCase(name)) {
                    targetMethod = declaredMethod;
                    break;
                }
            }
            if (targetMethod == null) {
                throw new InternalApiException(SystemCodeEnum.OPERATE_ERROR);
            }
            // 如果执行参数不为空的话，则证明这个方法存在入参
            Class<?> paramClass = null;
            if (StringUtils.isNotBlank(String.valueOf(map.get("parameter")))) {
                int parameterCount = targetMethod.getParameterCount();
                if (parameterCount != 1) {
                    throw new InternalApiException(SystemCodeEnum.OPERATE_ERROR);
                }
                Class<?>[] parameterTypes = targetMethod.getParameterTypes();
                // 获取到入参
                Class<?> parameterType = parameterTypes[0];
                String paramName = parameterType.getName();
                paramClass = Class.forName(paramName);
            }
            dto.setBean(bean);
            dto.setTargetMethod(targetMethod);
            dto.setParamClass(paramClass);
        } catch (Exception e) {
            throw new InternalApiException(SystemCodeEnum.OPERATE_ERROR);
        }
        return dto;
    }

    /**
     * 定时任务核心
     *
     * @param context quartz上下文信息
     */
    public static TaskConfig execute(JobExecutionContext context) {
        TaskConfig taskConfig = new TaskConfig();
        try {
            log.info("----------定时任务执行开始----------");
            JobDataMap scheduleJob = context.getMergedJobDataMap();
            BaseTaskHeadDTO dto = QuartzJobUtils.findMethod(scheduleJob);
            Method method;
            Object bean;
            Object object;
            taskConfig.setId(Long.parseLong(String.valueOf(scheduleJob.get("taskId"))));
            if (StringUtils.isNotBlank(String.valueOf(scheduleJob.get("parameter")))) {
                bean = JSON.parseObject(String.valueOf(scheduleJob.get("parameter")), dto.getParamClass());
                method = ReflectionUtils.findMethod(dto.getBean().getClass()
                        , String.valueOf(scheduleJob.get("methodName")), dto.getParamClass());
                if (ObjectUtil.isEmpty(bean)) {
                    taskConfig.setTaskMsg("参数异常");
                    log.info(JSONObject.toJSONString(taskConfig));
                    return taskConfig;
                }
                object = ReflectionUtils.invokeMethod(method, dto.getBean(), bean);
            } else {
                method = ReflectionUtils.findMethod(dto.getBean().getClass()
                        , String.valueOf(scheduleJob.get("methodName")));
                object = ReflectionUtils.invokeMethod(method, dto.getBean());
            }
            ResultVO<?> resultVO = new ResultVO<>();
            if (ObjectUtil.isNotNull(object)) {
                resultVO = (ResultVO) object;
            }
            taskConfig.setTaskMsg(resultVO.getMsg());
            log.info(resultVO.getMsg());
            log.info("----------定时任务执行结束----------");
        } catch (InternalApiException e) {
            log.error(e.getMessage());
            taskConfig.setTaskMsg(e.getMessage());
        }
        log.info(JSONObject.toJSONString(taskConfig));
        return taskConfig;
    }

    /**
     * 生成Cron表达式
     *
     * @param cron 表达式
     * @return 表达式
     */
    public static String createCronExpression(TaskConfigDTO cron) {
        Date date = cron.getStartDate();
        StringBuilder cronExp = new StringBuilder();
        if (null == cron.getIntervalTimeUnit()) {
            System.out.println("执行周期未配置");//执行周期未配置
        }
        //秒
        cronExp.append(date.getSeconds()).append(" ");
        //分
        cronExp.append(date.getMinutes()).append(" ");
        //小时
        cronExp.append(date.getHours()).append(" ");
        //每天
        if (TimeUnitEnum.DAY.getValue().equals(cron.getIntervalTimeUnit())) {
            cronExp.append("* ");//日
            cronExp.append("* ");//月
            cronExp.append("?");
        } else if (TimeUnitEnum.WEEK.getValue().equals(cron.getIntervalTimeUnit())) {
            if (ArrayUtil.isEmpty(cron.getDayOfWeeks())) {
                throw new InternalApiException(SystemCodeEnum.TASK_CRON_ERROR);
            }
            //周
            cronExp.append("? ");
            //月份
            cronExp.append("* ");
            //每周中的周几
            cronExp.append(getWeeksCron(cron.getDayOfWeeks()));
        } else if (TimeUnitEnum.MONTH.getValue().equals(cron.getIntervalTimeUnit())) {
            //每月中的哪几天
            if (ArrayUtil.isEmpty(cron.getDayOfMonths())) {
                throw new InternalApiException(SystemCodeEnum.TASK_CRON_ERROR);
            }
            cronExp.append(getDayOfMonthsCron(cron.getDayOfMonths()));
            //月份
            cronExp.append(" * ");
            //周
            cronExp.append("?");
        } else if (TimeUnitEnum.YEAR.getValue().equals(cron.getIntervalTimeUnit())) {
            //每年中的哪几天
            cronExp.append(getDayOfMonthsCron(cron.getDayOfMonths()));
            //月份
            cronExp.append(getDayOfMonthOfYearCron(cron.getMonths()));
            cronExp.append(" ?");
        }
        return cronExp.toString();
    }

    /**
     * 设置某年某月执行
     *
     * @param months 月份集合
     * @return 月的cron表达式
     */
    public static String getDayOfMonthOfYearCron(Integer[] months) {
        if (ArrayUtil.isEmpty(months)) {
            return "*";
        }
        StringBuilder cronExp = new StringBuilder();
        for (int i = 0; i < months.length; i++) {
            if (i == 0) {
                cronExp.append(" ").append(MonthEnum.getDesc(months[i]));
            } else {
                cronExp.append(",").append(MonthEnum.getDesc(months[i]));
            }
        }
        return cronExp.toString();
    }

    /**
     * 设置某月某一天执行
     *
     * @param days 天数集合
     * @return 日的cron表达式
     */
    public static String getDayOfMonthsCron(Integer[] days) {
        if (ArrayUtil.isEmpty(days)) {
            return "*";
        }
        StringBuilder cronExp = new StringBuilder();
        for (int i = 0; i < days.length; i++) {
            if (i == 0) {
                if (days[i] == 32) {
                    //本月最后一天
                    cronExp.append("L");
                } else {
                    cronExp.append(days[i]);
                }
            } else {
                cronExp.append(",").append(days[i]);
            }
        }
        return cronExp.toString();
    }

    /**
     * 设置某周某周几执行
     *
     * @param weeks 周数
     * @return 周的cron表达式
     */
    public static String getWeeksCron(Integer[] weeks) {
        StringBuilder cronExp = new StringBuilder();
        //周
        if (weeks.length >= 7) {
            cronExp.append("*");
        } else {
            for (int i = 0; i < weeks.length; i++) {
                if (i == 0) {
                    cronExp.append(WeekEnum.getDesc(weeks[i]));
                } else {
                    cronExp.append(",").append(WeekEnum.getDesc(weeks[i]));
                }
            }
        }
        return cronExp.toString();
    }

    /**
     * 按分组，停止删除定时任务
     *
     * @param groupName 分组名
     */
    public void schedulerDeleteAll(String groupName) {
        synchronized (log) {
            try {
                //只允许一个线程进入操作
                Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
                scheduler.pauseJobs(GroupMatcher.anyGroup());
                for (JobKey jobKey : set) {
                    if (jobKey.getName().contains(groupName)) {
                        //暂停所有JOB
                        scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                        //删除从数据库中注册的所有JOB
                        scheduler.deleteJob(jobKey);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new InternalApiException(SystemCodeEnum.TASK_DEL_EXCEPTION);
            }
        }
    }

}
