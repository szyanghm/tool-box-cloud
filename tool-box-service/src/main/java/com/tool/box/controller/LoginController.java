package com.tool.box.controller;

import com.tool.box.common.Contents;
import com.tool.box.common.SystemUrl;
import com.tool.box.dto.LoginDTO;
import com.tool.box.dto.UserRegisterDTO;
import com.tool.box.service.IUserInfoService;
import com.tool.box.utils.JwtUtils;
import com.tool.box.utils.TokenUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private TokenUtils tokenUtils;

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 登录
     *
     * @param dto 登录信息
     * @return 登录结果
     */
    @PostMapping(value = SystemUrl.login_url)
    public ResultVO<?> login(@RequestBody LoginDTO dto) {
        return userInfoService.login(dto);
    }

    /**
     * 退出登录
     *
     * @return 登出结果
     */
    @RequestMapping(value = SystemUrl.logout_url)
    public ResultVO<Object> logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //用户退出逻辑
        String token = request.getHeader(Contents.X_ACCESS_TOKEN);
        String account = JwtUtils.getAccount(token);
        tokenUtils.del(account);
        SecurityUtils.getSubject().logout();
        return ResultVO.success();
    }

    /**
     * 用户注册
     *
     * @param dto 注册信息
     * @return 注册结果
     */
    @PostMapping(value = SystemUrl.register_url)
    public ResultVO<?> register(@RequestBody @Validated UserRegisterDTO dto) {
        return userInfoService.register(dto);
    }

}
