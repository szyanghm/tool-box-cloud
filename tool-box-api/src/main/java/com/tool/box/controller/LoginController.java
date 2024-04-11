package com.tool.box.controller;

import com.tool.box.common.SystemUrl;
import com.tool.box.dto.LoginDTO;
import com.tool.box.dto.UserRegisterDTO;
import com.tool.box.feign.result.LoginConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class LoginController {

    @Resource
    private LoginConsumer loginConsumer;

    /**
     * 登录
     *
     * @param dto 登录参数
     * @return 登录成功/登录失败
     */
    @PostMapping(value = SystemUrl.login_url)
    public ResultVO<?> login(@RequestBody @Validated LoginDTO dto) {
        return loginConsumer.login(dto);
    }

    /**
     * 用户注册
     *
     * @param dto 注册信息
     * @return 注册结果
     */
    @PostMapping(value = SystemUrl.register_url)
    public ResultVO<?> register(@RequestBody @Validated UserRegisterDTO dto) {
        return loginConsumer.register(dto);
    }

    /**
     * 退出登录
     *
     * @return 登出结果
     */
    @RequestMapping(value = SystemUrl.logout_url)
    public ResultVO<Object> logout() {
        return loginConsumer.logout();
    }
}
