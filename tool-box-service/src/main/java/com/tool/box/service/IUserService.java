package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.common.InitUser;
import com.tool.box.model.User;

import java.util.List;

/**
 * <p>
 * 测试表 服务类
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
public interface IUserService extends IService<User> {

    /**
     * 查询账号、手机号
     *
     * @return 账号、手机号
     */
    List<InitUser> initUser();

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    User getByAccount(String account);


    /**
     * 根据用户账号查询密码
     *
     * @param account 用户账号
     * @return 密码
     */
    String getPassword(String account);


}
