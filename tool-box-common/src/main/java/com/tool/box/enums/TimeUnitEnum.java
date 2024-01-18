package com.tool.box.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author v_haimiyang
 * @Date 2024/1/10 11:18
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum TimeUnitEnum implements IEnum<String> {
    YEAR("YEAR", "年"),
    MONTH("MONTH", "月"),
    DAY("DAY", "天"),
    WEEK("WEEK", "周"),
    H("H", "小时"),
    M("M", "分钟"),
    S("S", "秒"),
    ;

    private String value;
    private String desc;

    @Override
    public String getValue() {
        return this.value;
    }

    public static String getDesc(String value) {
        for (TimeUnitEnum enumObj : TimeUnitEnum.values()) {
            if (enumObj.getValue().equals(value)) {
                return enumObj.getDesc();
            }
        }
        return null;
    }

    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (TimeUnitEnum enumObj : TimeUnitEnum.values()) {
            values.add(enumObj.value);
        }
        return values;
    }

    public static String getValuesStr() {
        List<String> values = new ArrayList<>();
        for (TimeUnitEnum enumObj : TimeUnitEnum.values()) {
            values.add(enumObj.value);
        }
        return values.stream().collect(Collectors.joining(","));
    }
}
