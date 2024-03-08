package com.tool.box.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tool.box.dto.MenuDTO;
import com.tool.box.model.Menu;
import com.tool.box.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单表 Mapper 接口
 *
 * @author v_haimiyang
 * @since 2024-02-04
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询菜单列表
     *
     * @param dto 入参
     * @return 菜单列表
     */
    List<MenuVO> findMenuList(@Param("dto") MenuDTO dto);

    /**
     * 根据id查询子集菜单列表
     * @param id
     * @return
     */
    List<MenuVO> getByIdList(String id);

}
