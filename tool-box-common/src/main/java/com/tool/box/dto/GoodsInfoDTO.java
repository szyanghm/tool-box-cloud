package com.tool.box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商品名")
    private String goodsName;

    @Schema(description = "商品简介")
    private String goodsIntro;

    @Schema(description = "关联分类id")
    private Long categoryId;

    @Schema(description = "关联轮播图表carousel_key")
    private String carouselKey;

    @Schema(description = "商品价格")
    private Integer originalPrice;

    @Schema(description = "成本价")
    private Integer costPrice;

    @Schema(description = "商品实际售价")
    private Integer sellingPrice;

    @Schema(description = "商品库存数量")
    private Integer stockNum;

    @Schema(description = "商品标签")
    private String tag;

    @Schema(description = "商品上架状态(上架:GOODS_UP, 下架:GOODS_DOWN)")
    private String status;

    @Schema(description = "商品附件")
    private List<File> list;
}
