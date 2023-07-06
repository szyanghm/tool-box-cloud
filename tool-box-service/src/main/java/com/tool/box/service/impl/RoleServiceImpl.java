package com.tool.box.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.mapper.RoleMapper;
import com.tool.box.model.Role;
import com.tool.box.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
