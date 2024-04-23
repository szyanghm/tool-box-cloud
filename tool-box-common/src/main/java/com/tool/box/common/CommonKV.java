package com.tool.box.common;

import lombok.Builder;
import lombok.Data;

/**
 * 共用V,K结构对象
 *
 * @Author v_haimiyang
 * @Date 2024/4/22 10:54
 * @Version 1.0
 */
@Data
@Builder
public class CommonKV {

    private String key;

    private String val;
}
