package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.model.User;
import org.springframework.stereotype.Repository;

/**
 * 用户表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户账号查询密码
     *
     * @param account 用户账号
     * @return 密码
     */
    String getPassword(String account);

}
