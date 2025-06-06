package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 商品信息表
 *
 * @author v_haimiyang
 * @since 2024-04-18
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_goods_info")
@Tag(name = "GoodsInfo对象", description = "商品信息表")
public class GoodsInfo extends BaseModel<GoodsInfo> {

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
}
