package com.tool.box.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.base.UserInfo;
import com.tool.box.mapper.UserDetailMapper;
import com.tool.box.model.UserDetail;
import com.tool.box.service.IUserDetailService;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements IUserDetailService {


    @Override
    public UserInfo getByAccount(String account) {
        return baseMapper.getByAccount(account);
    }
}
