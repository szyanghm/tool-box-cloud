package com.tool.box.controller;

import com.tool.box.dto.LoginDTO;
import com.tool.box.service.IUserInfoService;
import com.tool.box.utils.ApplicationContextUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 *
 * @Author v_haimiyang
 * @Date 2023/12/25 18:07
 * @Version 1.0
 */
@Slf4j
@RestController
public class LoginController {

    @Resource
    private IUserInfoService userInfoService;

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody @Validated LoginDTO dto) {
        return userInfoService.login(dto);
    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("LoginController");
            Object bean = ApplicationContextUtils.getBean(clazz);
            System.out.println(bean.getClass().getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
