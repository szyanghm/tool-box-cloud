package com.tool.box.controller;

import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.feign.result.UserInfoConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResultVO<UserInfo> getUser(@RequestParam("account") String account) {
        return userInfoConsumer.getUserInfo(account);
    }

    @GetMapping(value = "/save")
    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public ResultVO save() {
        return ResultVO.success(1);
    }






}
