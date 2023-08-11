package com.tool.box.service.impl;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.model.User;
import com.tool.box.service.IPermissionsService;
import com.tool.box.service.IUserInfoService;
import com.tool.box.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 测试表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

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
        Set<String> set = permissionsService.getPermissions(loginUser.getRole());
        loginUser.setAccount(user.getAccount());
        loginUser.setName(user.getName());
        loginUser.setPassword(user.getPassword());
        loginUser.setRole(user.getRole());
        loginUser.setSalt(user.getSalt());
        loginUser.setPermissions(new ArrayList<>(set));
        return loginUser;
    }
}
