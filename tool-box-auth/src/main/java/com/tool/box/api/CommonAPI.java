package com.tool.box.api;

import java.util.List;

/**
 * 公用接口
 *
 * @Author v_haimiyang
 * @Date 2023/8/16 15:55
 * @Version 1.0
 */
public interface CommonAPI {

    /**
     * 根据角色查询权限
     *
     * @param role 角色
     * @return 权限
     */
    List<String> getPermissions(String role);

}
