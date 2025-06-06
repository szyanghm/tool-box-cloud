package com.tool.box.common;

import lombok.Data;

import java.util.Map;

/**
 * 任务缓存对象
 *
 * @Author v_haimiyang
 * @Date 2025/5/29 16:48
 * @Version 1.0
 */
@Data
public class QuartzJobMap {

    private String key;
    private String val;
    private Long expireTime;

    private Map<String, String> params;

}
