package com.tool.box.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author v_haimiyang
 * @Date 2024/3/19 17:04
 * @Version 1.0
 */
@Data
public class DictDataVO {

    @ApiModelProperty("sys_dict_type字典类型")
    private String dictType;

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典键值")
    private String dictValue;

    @ApiModelProperty("字典排序")
    private Integer dictSort;

    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass;

    @ApiModelProperty("表格回显样式")
    private String listClass;

    @ApiModelProperty("是否默认（Y是 N否）")
    private String isDefault;

    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
