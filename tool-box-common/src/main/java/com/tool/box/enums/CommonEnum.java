package com.tool.box.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.tool.box.common.Contents;
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

    /**
     * 任务状态
     */
    R("R", "开启"),
    S("S", "暂停"),
    P("P", "关闭"),

    /**
     * 任务类型
     */
    REPEAT("repeat", "循环"),
    NOT_REPEAT("not_repeat", "次数"),

    /**
     * 角色权限
     */
    OP_WRITE_ADD(Contents.OP_WRITE_ADD, "新增"),
    OP_WRITE_UPDATE(Contents.OP_WRITE_UPDATE, "更新"),
    OP_READ(Contents.OP_READ, "查看"),
    ;

    private String value;
    private String desc;

    @Override
    public String getValue() {
        return this.value;
    }


}
