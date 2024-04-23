package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tool.box.model.MallCarousel;
import com.tool.box.vo.MallCarouselVO;
import org.springframework.stereotype.Repository;

/**
 * 轮播图表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2024-04-17
 */
@Repository
public interface MallCarouselMapper extends BaseMapper<MallCarousel> {

    /**
     * 查询轮播图分页列表
     *
     * @param page 分页参数
     * @return 轮播图分页列表
     */

    IPage<MallCarouselVO> findPage(Page page);
}
