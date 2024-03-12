package com.tool.box.service;

import com.tool.box.api.CommonAPI;
import com.tool.box.base.LoginUser;
import com.tool.box.feign.result.PermissionsConsumer;
import com.tool.box.feign.result.UserInfoConsumer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实现auth模块的CommonAPI接口
 *
 * @Author v_haimiyang
 * @Date 2023/8/16 16:00
 * @Version 1.0
 */
@Service
public class CommonAPIServiceImpl implements CommonAPI {

    @Resource
    private UserInfoConsumer userInfoConsumer;
    @Resource
    private PermissionsConsumer permissionsConsumer;

    @Override
    public LoginUser getLoginUser(String account) {
        return userInfoConsumer.getLoginUser(account);
    }

    @Override
    public List<String> getPermissions(String role) {
        return permissionsConsumer.getPermissions(role);
    }

}
