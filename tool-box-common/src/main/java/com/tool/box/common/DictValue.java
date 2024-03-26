package com.tool.box.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典接口数据对象
 *
 * @Author v_haimiyang
 * @Date 2024/3/19 16:01
 * @Version 1.0
 */
@Data
public class DictValue {

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典键值")
    private String dictValue;

    @ApiModelProperty("字典排序")
    private Integer dictSort;
}
