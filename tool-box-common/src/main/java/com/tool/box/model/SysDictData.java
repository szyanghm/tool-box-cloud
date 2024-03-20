package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 字典数据表
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("sys_dict_data")
@ApiModel(value = "SysDictData对象", description = "字典数据表")
public class SysDictData extends BaseModel<SysDictData> {

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
