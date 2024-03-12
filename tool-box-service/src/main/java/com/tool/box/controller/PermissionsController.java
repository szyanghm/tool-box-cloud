package com.tool.box.controller;

import com.tool.box.service.IPermissionsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限表 前端控制器
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/permissions")
public class PermissionsController {

    @Resource
    private IPermissionsService permissionsService;

    @PostMapping(value = "/getPermissions")
    public List<String> getPermissions(@RequestParam("role") String role) {
        return permissionsService.getPermissions(role);
    }
}
