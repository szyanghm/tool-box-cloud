package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.model.Permissions;

import java.util.List;
import java.util.Set;

/**
 * 权限表 服务类
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
public interface IPermissionsService extends IService<Permissions> {

    /**
     * 通过角色查询权限
     *
     * @param role 角色
     * @return 权限集合
     */
    Set<String> getPermissions(String role);

}
