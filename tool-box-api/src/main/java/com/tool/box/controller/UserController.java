package com.tool.box.controller;

import com.tool.box.feign.result.UserInfoConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试-控制器
 *
 * @Author v_haimiyang
 * @Date 2023/4/20 15:53
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserInfoConsumer userInfoConsumer;

    @GetMapping(value = "/getUser")
    public ResultVO getUser(@RequestParam("account") String account) {
        return userInfoConsumer.getUserInfo(account);
    }


}
