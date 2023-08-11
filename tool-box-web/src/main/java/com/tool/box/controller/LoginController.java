package com.tool.box.controller;

import com.tool.box.common.Contents;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 登录
     *
     * @param dto 登录参数
     * @return 登录成功/登录失败
     */
    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody LoginDTO dto) {
        if ("".equals(dto.getAccount().trim()) || "".equals(dto.getPassword().trim())) {
            return ResultVO.error(SystemCodeEnum.PASSWORD_ERROR);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(dto.getAccount(), dto.getPassword());
        SecurityUtils.getSubject().login(token);
        //addLoginRecord(getLoginUserId(), request);  // 记录登录信息
        HashMap<String, String> map = new HashMap<>();
        map.put(Contents.TOKEN, SecurityUtils.getSubject().getSession().getId().toString());
        log.info("登录成功");
        return ResultVO.success(map);
    }


}
