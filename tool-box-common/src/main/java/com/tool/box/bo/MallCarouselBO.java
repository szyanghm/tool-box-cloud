package com.tool.box.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 轮播图-bo
 *
 * @Author v_haimiyang
 * @Date 2024/4/23 16:17
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MallCarouselBO {

    private List<String> fileKeys;

    private String backCarouselKey;
    private String carouselKey;
}
