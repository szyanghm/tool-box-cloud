package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 轮播图表
 *
 * @author v_haimiyang
 * @since 2024-04-17
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_mall_carousel")
@Tag(name = "MallCarousel对象", description = "轮播图表")
public class MallCarousel extends BaseModel<MallCarousel> {

    @Schema(description = "轮播图key")
    private String carouselKey;

    @Schema(description = "关联附件表file_key")
    private String fileKey;

    @Schema(description = "点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    @Schema(description = "排序值(字段越大越靠前)")
    private Integer level;

}
