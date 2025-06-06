package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 菜单表
 *
 * @author v_haimiyang
 * @since 2024-02-04
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_menu")
@Tag(name = "Menu对象", description = "菜单表")
public class Menu extends BaseModel<Menu> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父级id")
    private Long parentId;

    @Schema(description = "菜单等级")
    private Integer level;

    @Schema(description = "菜单排序")
    private Integer sort;

    @Schema(description = "菜单路由")
    private String path;

}
