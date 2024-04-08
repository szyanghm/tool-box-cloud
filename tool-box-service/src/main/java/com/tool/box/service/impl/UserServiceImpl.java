package com.tool.box.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.common.InitUser;
import com.tool.box.mapper.UserMapper;
import com.tool.box.model.User;
import com.tool.box.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 测试表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<InitUser> initUser() {
        return baseMapper.initUser();
    }

    @Override
    public User getByAccount(String account) {
        return baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAccount, account)
                .or().eq(User::getPhone, account));
    }

    @Override
    public String getPassword(String account) {
        return baseMapper.getPassword(account);
    }


}
