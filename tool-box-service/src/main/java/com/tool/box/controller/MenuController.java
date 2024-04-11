package com.tool.box.controller;

import com.tool.box.dto.MenuDTO;
import com.tool.box.service.IMenuService;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private IMenuService menuService;

    @PostMapping(value = "/list")
    public ResultVO<?> getMenu(@RequestBody @Validated MenuDTO dto) {
        return ResultVO.success(menuService.findMenuList(dto));
    }

    @PostMapping(value = "/findList")
    public ResultVO<?> findList(@RequestBody @Validated MenuDTO dto) {
        return ResultVO.success(menuService.findList(dto));
    }
}
