package com.tool.box.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公用枚举
 *
 * @Author v_haimiyang
 * @Date 2024/1/2 10:15
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum CommonEnum implements IEnum<String> {

    R("R", "开启"),
    S("S", "暂停"),
    P("P", "关闭"),
    /**
     * 任务类型
     */
    REPEAT("repeat","循环"),
    /**
     * 任务类型
     */
    NOT_REPEAT("not_repeat","次数"),
    ;

    private String value;
    private String desc;

    @Override
    public String getValue() {
        return this.value;
    }


}
