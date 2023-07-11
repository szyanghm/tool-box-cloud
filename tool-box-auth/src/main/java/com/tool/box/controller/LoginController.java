package com.tool.box.controller;

import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.feign.UserInfoConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

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
    private UserInfoConsumer testConsumer;

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody LoginDTO dto) {
        if ("".equals(dto.getAccount().trim()) || "".equals(dto.getPassword().trim())) {
            return ResultVO.error(SystemCodeEnum.PASSWORD_ERROR);
        }
//        if (!CaptchaUtil.ver(vercode, request)) {
//            //CaptchaUtil.clear(request);  // 清除session中的验证码
//            return ResultVO.error(SystemCodeEnum.PASSWORD_ERROR);
//        }

        UsernamePasswordToken token = new UsernamePasswordToken(dto.getAccount(), dto.getPassword());
        SecurityUtils.getSubject().login(token);
        //addLoginRecord(getLoginUserId(), request);  // 记录登录信息
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", "1111111111111111111");  // 模拟登录令牌
        return ResultVO.success(map);
    }


    @PostMapping(value = "/loginOut")
    public ResultVO loginOut() {
        //addLoginRecord(getLoginUserId(), request);  // 记录登录信息
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", "222222222222222222222222");  // 模拟登录令牌
        return ResultVO.success(map);
    }

    @PostMapping(value = "/test")
    public ResultVO test() {
        //addLoginRecord(getLoginUserId(), request);  // 记录登录信息
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", "33333333333");  // 模拟登录令牌
        return ResultVO.success(map);
    }

    @PostMapping(value = "/api")
    public ResultVO api() {
        //addLoginRecord(getLoginUserId(), request);  // 记录登录信息
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", "44444444444444444");  // 模拟登录令牌
        return ResultVO.success(map);
    }

}
