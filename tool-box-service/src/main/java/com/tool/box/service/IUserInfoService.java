package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.base.UserInfo;
import com.tool.box.model.User;

/**
 * <p>
 * 测试表 服务类
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
public interface IUserInfoService {


    UserInfo getUserInfo(String account);
}
