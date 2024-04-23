package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "MallCarousel对象", description = "轮播图表")
public class MallCarousel extends BaseModel<MallCarousel> {

    @ApiModelProperty("轮播图key")
    private String carouselKey;

    @ApiModelProperty("关联附件表file_key")
    private String fileKey;

    @ApiModelProperty("点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    @ApiModelProperty("排序值(字段越大越靠前)")
    private Integer level;

}
