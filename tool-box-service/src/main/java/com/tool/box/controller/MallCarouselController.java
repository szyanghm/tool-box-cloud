package com.tool.box.controller;

import com.tool.box.common.SystemUrl;
import com.tool.box.dto.MallCarouselDTO;
import com.tool.box.service.IMallCarouselService;
import com.tool.box.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 轮播图表 前端控制器
 *
 * @author v_haimiyang
 * @since 2024-04-17
 */
@RestController
@RequestMapping("/mallCarousel")
public class MallCarouselController {

    @Resource
    private IMallCarouselService mallCarouselService;

    /**
     * 登录
     *
     * @param dto 登录信息
     * @return 登录结果
     */
    @PostMapping(value = SystemUrl.findPage)
    public ResultVO<?> findPage(@RequestBody MallCarouselDTO dto) {
        return ResultVO.success(mallCarouselService.findPage(dto));
    }


}
