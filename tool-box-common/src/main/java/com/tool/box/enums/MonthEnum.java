package com.tool.box.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日期月份枚举
 *
 * @Author v_haimiyang
 * @Date 2024/1/10 15:29
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum MonthEnum implements IEnum<Integer> {

    /**
     * 一月
     */
    JAN(1, "JAN"),
    /**
     * 二月
     */
    FEB(2, "FEB"),
    /**
     * 三月
     */
    MAR(3, "MAR"),
    /**
     * 四月
     */
    APRQ(4, "APRQ"),
    /**
     * 五月
     */
    MAY(5, "MAY"),
    /**
     * 六月
     */
    JUN(6, "JUN"),
    /**
     * 7月
     */
    JUL(7, "JUL"),
    /**
     * 8月
     */
    AUG(8, "AUG"),
    /**
     * 9月
     */
    SEP(9, "SEP"),
    /**
     * 10月
     */
    OCT(10, "OCT"),
    /**
     * 11月
     */
    NOV(11, "NOV"),
    /**
     * 12月
     */
    DEC(12, "DEC"),
    ;

    private Integer value;
    private String desc;


    public static String getDesc(Integer value) {
        for (MonthEnum enumObj : MonthEnum.values()) {
            if (value >= 12) {
                return DEC.getDesc();
            }
            if (enumObj.getValue() == value) {
                return enumObj.getDesc();
            }
        }
        return null;
    }
}
