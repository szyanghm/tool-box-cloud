package com.tool.box.service.impl;

import com.tool.box.base.UserInfo;
import com.tool.box.model.User;
import com.tool.box.service.IUserInfoService;
import com.tool.box.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public UserInfo getUserInfo(String account) {
        UserInfo userInfo = new UserInfo();
        User user = userService.getByAccount(account);
        if (user == null) {
            return null;
        }
        userInfo.setAccount(user.getAccount());
        userInfo.setName(user.getName());
        userInfo.setPassword(user.getPassword());
        userInfo.setRole(user.getRole());
        userInfo.setSalt(user.getSalt());
        return userInfo;
    }
}
