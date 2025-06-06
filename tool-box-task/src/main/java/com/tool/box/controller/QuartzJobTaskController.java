package com.tool.box.controller;

import com.tool.box.common.Contents;
import com.tool.box.dto.QuartzJobDTO;
import com.tool.box.dto.TaskConfigDTO;
import com.tool.box.service.IQuartzJobService;
import com.tool.box.service.ITaskConfigService;
import com.tool.box.service.QuartzJobTaskService;
import com.tool.box.service.TaskService;
import com.tool.box.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 定时任务表 前端控制器
 *
 * @author v_haimiyang
 * @since 2023-12-29
 */
@Slf4j
@RestController
@Api(value = "定时任务控制器")
public class QuartzJobTaskController {

    @Resource
    private IQuartzJobService quartzJobService;
    @Resource
    private QuartzJobTaskService quartzJobTaskService;
    @Resource
    private ITaskConfigService taskConfigService;
    @Resource
    private TaskService taskService;

    @PostMapping(value = "/findList")
    @ApiOperation(value = "定时任务列表", notes = "2000成功/5500失败")
    public ResultVO<?> findList(@RequestParam("id") String id) {
        return ResultVO.success(taskConfigService.findList(id));
    }

    /**
     * 新增定时任务模块
     *
     * @param dto 入参
     * @return 新增结果
     */
    @PostMapping(value = "/addQuartzJob")
    @ApiOperation(value = "新增定时任务模块", notes = "2000成功/5500失败")
    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public ResultVO<?> addQuartzJob(@RequestBody QuartzJobDTO dto) {
        return quartzJobService.add(dto);
    }

    /**
     * 新增定时任务
     *
     * @param dto 入参
     * @return 新增结果
     */
    @PostMapping(value = "/addTask")
    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public ResultVO<?> addTask(@RequestBody TaskConfigDTO dto) {
        return taskService.add(dto);
    }

    @PostMapping(value = "/refresh")
    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public void refreshJobs() {
        taskService.refreshJobs();
    }

    @PostMapping(value = "/refreshAll")
//    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public void refreshAllJobs() {
        quartzJobTaskService.refreshAllJobs();
    }

}
