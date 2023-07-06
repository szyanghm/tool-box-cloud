package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.model.Permissions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Repository
public interface PermissionsMapper extends BaseMapper<Permissions> {

    /**
     * 通过角色查询权限
     *
     * @param role 角色
     * @return 权限集合
     */
    List<String> getPermissions(String role);

}
