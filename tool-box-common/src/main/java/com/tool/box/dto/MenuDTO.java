package com.tool.box.dto;

import com.tool.box.common.validata.EnumValue;
import lombok.Data;

/**
 * 查询菜单-DTO
 *
 * @Author v_haimiyang
 * @Date 2024/2/5 9:02
 * @Version 1.0
 */
@Data
public class MenuDTO {

    /**
     * 角色code
     */
    private String roleCode;
    /**
     * api:后台管理、mini:小程序
     */
    @EnumValue(strValues = {"api", "mini"}, isRequire = true)
    private String source;
}
