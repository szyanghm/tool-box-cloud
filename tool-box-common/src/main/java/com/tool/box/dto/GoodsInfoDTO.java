package com.tool.box.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * 商品信息表
 *
 * @author v_haimiyang
 * @since 2024-04-18
 */
@Data
public class GoodsInfoDTO {

    @ApiModelProperty("商品名")
    private String goodsName;

    @ApiModelProperty("商品简介")
    private String goodsIntro;

    @ApiModelProperty("关联分类id")
    private Long categoryId;

    @ApiModelProperty("关联轮播图表carousel_key")
    private String carouselKey;

    @ApiModelProperty("商品价格")
    private Integer originalPrice;

    @ApiModelProperty("成本价")
    private Integer costPrice;

    @ApiModelProperty("商品实际售价")
    private Integer sellingPrice;

    @ApiModelProperty("商品库存数量")
    private Integer stockNum;

    @ApiModelProperty("商品标签")
    private String tag;

    @ApiModelProperty("商品上架状态(上架:GOODS_UP, 下架:GOODS_DOWN)")
    private String status;

    @ApiModelProperty("商品附件")
    private List<File> list;
}
