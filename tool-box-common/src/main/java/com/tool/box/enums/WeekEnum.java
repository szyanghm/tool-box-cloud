package com.tool.box.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期周枚举
 *
 * @Author v_haimiyang
 * @Date 2024/1/10 15:29
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum WeekEnum implements IEnum<Integer> {

    /**
     * 周一
     */
    MON(1, "MON"),
    /**
     * 周二
     */
    TUE(2, "TUE"),
    /**
     * 周三
     */
    WED(3, "WED"),
    /**
     * 周四
     */
    THU(4, "THU"),
    /**
     * 周五
     */
    FRI(5, "FRI"),
    /**
     * 周六
     */
    SAT(6, "SAT"),
    /**
     * 周日
     */
    SUN(7, "SUN"),
    ;

    private Integer value;
    private String desc;


    public static String getDesc(Integer value) {
        for (WeekEnum enumObj : WeekEnum.values()) {
            if (value >= 7) {
                return SUN.getDesc();
            }
            if (enumObj.getValue() == value) {
                return enumObj.getDesc();
            }
        }
        return null;
    }
}
