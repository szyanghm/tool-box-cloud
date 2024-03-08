package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.dto.MenuDTO;
import com.tool.box.model.Menu;
import com.tool.box.vo.MenuVO;

import java.util.List;

/**
 * 菜单表 服务类
 *
 * @author v_haimiyang
 * @since 2024-02-04
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询菜单列表
     *
     * @param dto 入参
     * @return 菜单列表
     */
    List<MenuVO> findMenuList(MenuDTO dto);
}
