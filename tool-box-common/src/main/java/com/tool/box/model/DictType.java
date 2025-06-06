package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@TableName("t_dict_type")
@Tag(name = "SysDictType对象", description = "字典类型表")
public class DictType extends BaseModel<DictType> {

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态（0正常 1停用）")
    private String status;

    @Schema(description = "备注")
    private String remark;

}
