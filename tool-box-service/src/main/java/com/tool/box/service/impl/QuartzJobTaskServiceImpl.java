package com.tool.box.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.mapper.QuartzJobTaskMapper;
import com.tool.box.service.IQuartzJobTaskService;
import com.tool.box.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/1/12 18:21
 * @Version 1.0
 */
@DS("task")
@Service
public class QuartzJobTaskServiceImpl implements IQuartzJobTaskService {

    @Resource
    private QuartzJobTaskMapper quartzJobTaskMapper;

    @Override
    public List<QuartzJobTask> findJobTaskList(String groupName) {
        return quartzJobTaskMapper.findJobTaskList(groupName);
    }

    @Override
    public ResultVO<?> updateTaskConfig(TaskMsgDTO dto) {
        if (quartzJobTaskMapper.updateTaskConfig(dto) > 0) {
            return ResultVO.success();
        }
        return ResultVO.fail();
    }
}
