package com.tool.box.service;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.dto.LoginDTO;
import com.tool.box.vo.ResultVO;

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
     * 根据用户账户查询登录用户信息-用于登录校验（包含密码）
     *
     * @param account 用户账户
     * @return 登录用户信息
     */
    LoginUser getLoginUser(String account);

    /**
     * 根据用户账户查询登录用户信息-用于登录校验(不包含密码)
     *
     * @param account 用户账户
     * @return 登录用户信息
     */
    UserInfo getUserInfo(String account);

    /**
     * 登录
     *
     * @param dto 登录参数
     * @return 登录用户信息
     */
    ResultVO login(LoginDTO dto);

}
