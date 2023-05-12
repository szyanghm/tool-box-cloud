package com.tool.box.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统工具类
 *
 * @Author v_haimiyang
 * @Date 2023/4/14 16:32
 * @Version 1.0
 */
@Slf4j
public class SystemUtils {

    /**
     * 获取系统环境变量配置
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getSystemProperty(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (StringUtils.isBlank(value)) {
            value = System.getenv(name);
            if (StringUtils.isBlank(value)) {
                log.info("未读取到系统配置");
            }
        }
        return value;
    }
}
