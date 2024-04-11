package com.tool.box.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.dto.MenuDTO;
import com.tool.box.mapper.MenuMapper;
import com.tool.box.model.Menu;
import com.tool.box.service.IMenuService;
import com.tool.box.utils.TreeUtils;
import com.tool.box.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-02-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<MenuVO> findMenuList(MenuDTO dto) {
        LoginUser loginUser = LocalProvider.getUser();
        dto.setRoleCode(loginUser.getRoleCode());
        List<MenuVO> list = baseMapper.findMenuList(dto);
        List<MenuVO> voList = TreeUtils.bulid(list, "0").get(0).getChildren();
        return voList;
    }

    @Override
    public List<MenuVO> findList(MenuDTO dto) {
        return baseMapper.findMenuList(dto);
    }
}
