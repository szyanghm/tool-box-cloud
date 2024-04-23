package com.tool.box.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 轮播图-vo
 *
 * @Author v_haimiyang
 * @Date 2024/4/17 16:27
 * @Version 1.0
 */
@Data
public class MallCarouselVO {

    @ApiModelProperty("主键唯一id")
    private String id;

    @ApiModelProperty("轮播图key")
    private String carouselKey;

    @ApiModelProperty("轮播图")
    private String carouselImage;

    @ApiModelProperty("点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    @ApiModelProperty("排序值(字段越大越靠前)")
    private Integer level;
}
