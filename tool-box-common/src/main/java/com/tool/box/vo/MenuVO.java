package com.tool.box.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author v_haimiyang
 * @Date 2024/2/4 15:03
 * @Version 1.0
 */
@Data
public class MenuVO {

    /**
     * 唯一id
     */
    private String id;
    /**
     * 系统来源划分
     */
    private String source;
    /**
     * 菜单唯一code
     */
    private String menuCode;

    /**
     * 父级id
     */
    private String parentCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单等级
     */
    private int level;

    /**
     * 排序-序号
     */
    private int sort;

    /**
     * 路由
     */
    private String path;

    /**
     * 子集菜单
     */
    private List<MenuVO> children = null;

    public void add(MenuVO node) {
        children.add(node);
    }
}
