package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.dto.QuartzJobDTO;
import com.tool.box.model.QuartzJob;
import com.tool.box.vo.ResultVO;

import java.util.List;

/**
 * 定时任务表 服务类
 *
 * @author v_haimiyang
 * @since 2023-12-29
 */
public interface IQuartzJobService extends IService<QuartzJob> {

    /**
     * 新增任务
     *
     * @return 新增结果
     */
    ResultVO<?> add(QuartzJobDTO dto);

    /**
     * 查询任务状态为S的任务列表
     *
     * @param states 任务状态集合
     * @return 任务状态为S的任务列表
     */
    ResultVO<List<QuartzJob>> findList(List<String> states);
}
