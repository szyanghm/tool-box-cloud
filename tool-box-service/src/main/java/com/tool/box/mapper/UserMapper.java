package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.common.InitUser;
import com.tool.box.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询账号、手机号
     *
     * @return 账号、手机号
     */
    List<InitUser> initUser();

    /**
     * 根据用户账号查询密码
     *
     * @param account 用户账号
     * @return 密码
     */
    String getPassword(String account);

}
