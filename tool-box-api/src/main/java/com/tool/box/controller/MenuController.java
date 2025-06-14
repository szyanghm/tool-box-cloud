package com.tool.box.controller;

import com.tool.box.common.Contents;
import com.tool.box.config.SystemConfig;
import com.tool.box.dto.MenuDTO;
import com.tool.box.feign.result.MenuConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单表 前端控制器
 *
 * @author v_haimiyang
 * @since 2024-02-04
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private MenuConsumer menuConsumer;

    @PostMapping(value = "/list")
    public ResultVO<?> getMenu() {
        MenuDTO dto = new MenuDTO();
        String contextPath = systemConfig.getEnvironment().getRequiredProperty(Contents.CONTEXT_PATH);
        dto.setSource(contextPath);
        return menuConsumer.getMenu(dto);
    }

    @PostMapping(value = "/findList")
    public ResultVO<?> findList() {
        MenuDTO dto = new MenuDTO();
        String contextPath = systemConfig.getEnvironment().getRequiredProperty(Contents.CONTEXT_PATH);
        dto.setSource(contextPath);
        return menuConsumer.findList(dto);
    }
}
