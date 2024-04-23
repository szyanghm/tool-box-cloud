package com.tool.box.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.bo.MallCarouselBO;
import com.tool.box.dto.MallCarouselDTO;
import com.tool.box.mapper.MallCarouselMapper;
import com.tool.box.model.MallCarousel;
import com.tool.box.service.IMallCarouselService;
import com.tool.box.vo.MallCarouselVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 轮播图表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-04-17
 */
@Service
public class MallCarouselServiceImpl
        extends ServiceImpl<MallCarouselMapper, MallCarousel>
        implements IMallCarouselService {

    @Override
    public IPage<MallCarouselVO> findPage(MallCarouselDTO dto) {
        IPage<MallCarouselVO> page = baseMapper.findPage(dto.getPage());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> add(MallCarouselBO bo) {
        List<String> fileKeys = bo.getFileKeys();
        List<MallCarousel> carouselList = baseMapper.selectList(new QueryWrapper<MallCarousel>().lambda()
                .eq(MallCarousel::getCarouselKey, bo.getBackCarouselKey()));
        List<String> backFileKeys = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(carouselList)) {
            backFileKeys = carouselList.stream().map(MallCarousel::getFileKey).collect(Collectors.toList());
        }
        List<MallCarousel> list = new ArrayList<>();
        for (int i = 0; i < fileKeys.size(); i++) {
            MallCarousel carousel = new MallCarousel();
            carousel.setFileKey(fileKeys.get(i));
            carousel.setCarouselKey(bo.getCarouselKey());
            carousel.setLevel(i);
            list.add(carousel);
        }
        this.saveBatch(list);
        if (StringUtils.isNotBlank(bo.getBackCarouselKey())) {
            deleteByCarouselKey(bo.getBackCarouselKey());
        }
        return backFileKeys;
    }

    @Async
    @Override
    public void deleteByCarouselKey(String carouselKey) {
        this.remove(new QueryWrapper<MallCarousel>().lambda().eq(MallCarousel::getCarouselKey, carouselKey));
    }
}
