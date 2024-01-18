package com.tool.box.api;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;

import java.util.List;

/**
 * 公用接口
 *
 * @Author v_haimiyang
 * @Date 2023/8/16 15:55
 * @Version 1.0
 */
public interface CommonAPI {

    /**
     * 根据账号查询用户信息
     *
     * @param account 用户账号
     * @return 用户登录信息
     */
    LoginUser getLoginUser(String account);

    /**
     * 根据账号查询用户信息
     *
     * @param account 用户账号
     * @return 用户登录信息
     */
    UserInfo getUserInfo(String account);

    /**
     * 根据角色查询权限
     *
     * @param role 角色
     * @return 权限
     */
    List<String> getPermissions(String role);

}
