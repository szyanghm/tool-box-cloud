package com.tool.box.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态数据对象
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 16:34
 * @Version 1.0
 */
public class DataStatic {

    public static List<String> profileDataList = new ArrayList<>();

    static {
        profileDataList.add("dev");
        profileDataList.add("test");
    }
}
