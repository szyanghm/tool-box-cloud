package com.tool.box.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.dto.QuartzJobDTO;
import com.tool.box.mapper.QuartzJobMapper;
import com.tool.box.model.QuartzJob;
import com.tool.box.service.IQuartzJobService;
import com.tool.box.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-12-29
 */
@Service
public class QuartzJobServiceImpl
        extends ServiceImpl<QuartzJobMapper, QuartzJob>
        implements IQuartzJobService {

    @Override
    public ResultVO<?> add(QuartzJobDTO dto) {
        QuartzJob job = new QuartzJob();
        BeanUtils.copyProperties(dto, job);
        job.setParameter(JSONObject.toJSONString(dto.getParameter()));
        if (StringUtils.isNotBlank(dto.getId())) {
            job.setId(Long.valueOf(dto.getId()));
        }
        if (this.saveOrUpdate(job)) {
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

    @Override
    public ResultVO<List<QuartzJob>> findList(List<String> states) {
        List<QuartzJob> list = baseMapper.selectList(new QueryWrapper<QuartzJob>().lambda()
//                .in(QuartzJob::getState, states)
        );
        return ResultVO.success(list);
    }
}
