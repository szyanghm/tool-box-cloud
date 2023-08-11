package com.tool.box.service;

import com.tool.box.base.LoginUser;

/**
 * <p>
 * 测试表 服务类
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
public interface IUserInfoService {


    /**
     * 根据用户账户查询登录用户信息-用于登录校验
     *
     * @param account 用户账户
     * @return 登录用户信息
     */
    LoginUser getLoginUser(String account);
}
