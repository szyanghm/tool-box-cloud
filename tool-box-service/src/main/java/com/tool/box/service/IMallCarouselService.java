package com.tool.box.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.bo.MallCarouselBO;
import com.tool.box.dto.MallCarouselDTO;
import com.tool.box.model.MallCarousel;
import com.tool.box.vo.MallCarouselVO;

import java.util.List;

/**
 * 轮播图表 服务类
 *
 * @author v_haimiyang
 * @since 2024-04-17
 */
public interface IMallCarouselService extends IService<MallCarousel> {

    /**
     * 查询轮播图分页列表
     *
     * @param dto 入参
     * @return 轮播图分页列表
     */
    IPage<MallCarouselVO> findPage(MallCarouselDTO dto);

    /**
     * 新增轮播图
     *
     * @param bo 入参
     * @return 原fileKey
     */
    List<String> add(MallCarouselBO bo);

    void deleteByCarouselKey(String carouselKey);

}
