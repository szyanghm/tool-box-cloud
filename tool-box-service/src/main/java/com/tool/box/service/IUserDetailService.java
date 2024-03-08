package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.model.UserDetail;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
public interface IUserDetailService extends IService<UserDetail> {

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    UserInfo getByAccount(String account);

}
