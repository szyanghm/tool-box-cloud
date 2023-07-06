package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.model.User;

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
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    User getByAccount(String account);

}
