package com.tool.box.service.impl;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.jwt.JwtToken;
import com.tool.box.model.User;
import com.tool.box.service.IPermissionsService;
import com.tool.box.service.IUserInfoService;
import com.tool.box.service.IUserService;
import com.tool.box.utils.JwtUtils;
import com.tool.box.utils.SystemUtils;
import com.tool.box.utils.TokenUtils;
import com.tool.box.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private IUserService userService;
    @Resource
    private IPermissionsService permissionsService;

    @Override
    public LoginUser getLoginUser(String account) {
        LoginUser loginUser = new LoginUser();
        User user = userService.getByAccount(account);
        if (user == null) {
            return null;
        }
        List<String> list = permissionsService.getPermissions(user.getRole());
        loginUser.setAccount(user.getAccount());
        loginUser.setName(user.getName());
        loginUser.setPassword(user.getPassword());
        loginUser.setRole(user.getRole());
        loginUser.setSalt(user.getSalt());
        loginUser.setStatus(user.getStatus());
        loginUser.setPermissions(list);
        return loginUser;
    }

    @Override
    public UserInfo getUserInfo(String account) {
        User user = userService.getByAccount(account);
        if (user == null) {
            return null;
        }
        return SystemUtils.getUserInfo(user);
    }

    @Override
    public ResultVO login(LoginDTO dto) {
        User user = userService.getByAccount(dto.getAccount());
        if (user == null) {
            return ResultVO.error(SystemCodeEnum.USER_DOES_NOT_EXIST);
        }
        if (user.getStatus() == 1) {
            return ResultVO.error(SystemCodeEnum.USER_LOCK_ING);
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return ResultVO.error(SystemCodeEnum.PASSWORD_ERROR);
        }
        UserInfo userInfo = SystemUtils.getUserInfo(user);
        String token = JwtUtils.createToken(userInfo);
        // 设置超时时间
        tokenUtils.setToken(token, userInfo.getAccount());
        SecurityUtils.getSubject().login(new JwtToken(token));
        return ResultVO.success(token);
    }

}
