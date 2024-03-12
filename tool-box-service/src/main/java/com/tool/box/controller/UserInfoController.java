package com.tool.box.controller;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.service.IUserInfoService;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户-控制器
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

    @PostMapping(value = "/getUserInfo")
    public ResultVO<UserInfo> getUserInfo(@RequestParam("account") String account) {
        UserInfo userInfo = userInfoService.getUserInfo(account);
        return ResultVO.success(userInfo);
    }

    @PostMapping(value = "/getLoginUser")
    public LoginUser getLoginUser(@RequestParam("account") String account) {
        return userInfoService.getLoginUser(account);
    }

}
