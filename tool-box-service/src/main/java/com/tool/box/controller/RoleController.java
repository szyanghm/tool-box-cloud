package com.tool.box.controller;

import com.tool.box.base.LocalProvider;
import com.tool.box.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @PostMapping(value = "/test")
    public ResultVO getPermissions() {
        System.out.println("role/test");
        return ResultVO.success(LocalProvider.getUser());
    }

}
