package com.tool.box.controller;

import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.feign.result.UserConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    private UserConsumer userInfoConsumer;

    /**
     * 获取用户信息
     *
     * @param account 用户账号
     * @return 用户信息
     */
    @GetMapping(value = "/getUser")
    public ResultVO<UserInfo> getUser(@RequestParam("account") String account) {
        return userInfoConsumer.getUserInfo(account);
    }

    @GetMapping(value = "/add")
    @RequiresPermissions(value = {Contents.OP_WRITE_ADD, Contents.OP_WRITE_UPDATE}, logical = Logical.OR)
    public ResultVO add() {
        return ResultVO.success(1);
    }

    /**
     * 上传用户头像
     *
     * @param file 头像图片
     * @return 上传结果
     */
    @RequestMapping(value = "/updateUserAvatar")
    public ResultVO updateUserAvatar(MultipartFile file) {
        return userInfoConsumer.updateUserAvatar(file);
    }


}
