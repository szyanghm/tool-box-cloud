package com.tool.box.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskConfigDTO;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.mapper.TaskConfigMapper;
import com.tool.box.model.TaskConfig;
import com.tool.box.service.ITaskConfigService;
import com.tool.box.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务执行表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-01-08
 */
@Service
public class TaskConfigServiceImpl
        extends ServiceImpl<TaskConfigMapper, TaskConfig>
        implements ITaskConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<String> add(TaskConfigDTO dto) {
        TaskConfig config = new TaskConfig();
        BeanUtils.copyProperties(dto, config);
        config.setParameter(JSONObject.toJSONString(dto.getParameter()));
        if (baseMapper.insert(config) > 0) {
            return ResultVO.success(String.valueOf(config.getId()));
        }
        return ResultVO.fail();
    }

    @Override
    public ResultVO<?> updateTaskConfig(TaskMsgDTO dto) {
        if (baseMapper.update(null, new UpdateWrapper<TaskConfig>().lambda()
                .set(TaskConfig::getTaskMsg, dto.getTaskMsg())
                .eq(TaskConfig::getId, dto.getId())
        ) > 0) {
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

    @Override
    public List<QuartzJobTask> findJobTaskList(List<String> states) {
        return baseMapper.findJobTaskList(states);
    }

    @Override
    public QuartzJobTask getJobTask(String id) {
        return baseMapper.getJobTask(id);
    }
}
