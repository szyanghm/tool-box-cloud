package com.tool.box.controller;

import com.tool.box.common.QuartzJobMap;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.service.IQuartzJobTaskService;
import com.tool.box.utils.RedissonLockUtil;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务表 前端控制器
 *
 * @author v_haimiyang
 * @since 2023-12-29
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class QuartzJobTaskController {

    @Resource
    private RedissonLockUtil redissonLockUtil;

    @Resource
    private IQuartzJobTaskService quartzJobTaskService;

    /**
     * 按任务状态查询执行的任务
     *
     * @return 任务列表
     */
    @PostMapping(value = "/findJobTaskList")
    public List<QuartzJobTask> findJobTaskList(@RequestParam("groupName") String groupName) {
        return quartzJobTaskService.findJobTaskList(groupName);
    }

    /**
     * 更新执行任务消息
     */
    @PostMapping(value = "/updateTaskConfig")
    public ResultVO<?> updateTaskConfig(@RequestBody TaskMsgDTO dto) {
        return quartzJobTaskService.updateTaskConfig(dto);
    }

    /**
     * 获取锁
     *
     * @param jobMap 加锁对象
     * @return 获取结果
     */
    @PostMapping(value = "/getLock")
    public ResultVO<?> getLock(@RequestBody QuartzJobMap jobMap) {
        if (redissonLockUtil.tryLock(jobMap.getKey())) {
            return ResultVO.success();
        }
        return ResultVO.error(SystemCodeEnum.OPERATE_ERROR, "未获取锁");
    }

    /**
     * 释放锁
     *
     * @param jobMap 加锁对象
     * @return 释放结果
     */
    @PostMapping(value = "/releaseLock")
    public ResultVO<?> releaseLock(@RequestBody QuartzJobMap jobMap) {
        redissonLockUtil.unlock(jobMap.getKey());
        return ResultVO.success();
    }

}
