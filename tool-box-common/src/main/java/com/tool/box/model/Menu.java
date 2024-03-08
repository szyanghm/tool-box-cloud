package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu extends BaseModel<Menu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("菜单等级")
    private Integer level;

    @ApiModelProperty("菜单排序")
    private Integer sort;

    @ApiModelProperty("菜单路由")
    private String path;

}
