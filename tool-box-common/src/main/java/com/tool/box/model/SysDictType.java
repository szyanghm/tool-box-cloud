package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 字典类型表
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("sys_dict_type")
@ApiModel(value = "SysDictType对象", description = "字典类型表")
public class SysDictType extends BaseModel<SysDictType> {

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("备注")
    private String remark;

}
