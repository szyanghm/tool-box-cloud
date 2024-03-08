package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.base.UserInfo;
import com.tool.box.model.UserDetail;
import org.springframework.stereotype.Repository;

/**
 * 用户信息表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
@Repository
public interface UserDetailMapper extends BaseMapper<UserDetail> {


    UserInfo getByAccount(String account);

}
