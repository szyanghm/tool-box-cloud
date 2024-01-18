package com.tool.box.controller;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.dto.LoginDTO;
import com.tool.box.feign.UserInfoConsumer;
import com.tool.box.feign.result.ResultUserInfoConsumer;
import com.tool.box.utils.JwtUtils;
import com.tool.box.utils.TokenUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private TokenUtils tokenUtils;

    @Resource
    private UserInfoConsumer userInfoConsumer;
    @Resource
    private ResultUserInfoConsumer resultUserInfoConsumer;

    /**
     * 登录
     *
     * @param dto 登录参数
     * @return 登录成功/登录失败
     */
    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody @Validated LoginDTO dto) {
        String password = new SimpleHash(Sha256Hash.ALGORITHM_NAME, dto.getPassword(), ByteSource.Util.bytes("tool"), 1024).toBase64();
        dto.setPassword(password);
        return resultUserInfoConsumer.login(dto);
    }

    /**
     * 退出登录
     *
     * @param request http请求参数
     * @return
     */
    @RequestMapping(value = "/logout")
    public ResultVO<Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader(Contents.X_ACCESS_TOKEN);
        String account = JwtUtils.getAccount(token);
        tokenUtils.del(account);
        SecurityUtils.getSubject().logout();
        return ResultVO.success();
    }

    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @RequiresPermissions(value = {"op:read"})
    @PostMapping(value = "/getUserInfo")
    public ResultVO getUserInfo() {
        UserInfo userInfo = LocalProvider.getUser();
        return ResultVO.success(userInfo);
    }

    @PostMapping(value = "/test")
    public ResultVO test() {
        UserInfo userInfo = userInfoConsumer.getUserInfo("admin");
        System.out.println(JSONObject.toJSONString(userInfo));
        LoginUser loginUser = userInfoConsumer.getLoginUser("admin");
        System.out.println(JSONObject.toJSONString(loginUser));
        List<String> list = userInfoConsumer.getPermissions("admin");
        return ResultVO.success(list);
    }


}
