package com.tool.box.controller;

import com.tool.box.base.LoginUser;
import com.tool.box.service.IPermissionsService;
import com.tool.box.service.IUserInfoService;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 测试-控制器
 *
 * @Author v_haimiyang
 * @Date 2023/4/20 15:53
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IPermissionsService permissionsService;

    @PostMapping(value = "/getLoginUser")
    public ResultVO<LoginUser> getLoginUser(@RequestParam("account") String account) {
        LoginUser loginUser = userInfoService.getLoginUser(account);
        return ResultVO.success(loginUser);
    }


    @PostMapping(value = "/getPermissions")
    public ResultVO<Set<String>> getPermissions(@RequestParam("role") String role) {
        return ResultVO.success(permissionsService.getPermissions(role));
    }


}
