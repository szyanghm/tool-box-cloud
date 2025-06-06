package com.tool.box.enums;

/**
 * @Author v_haimiyang
 * @Date 2025/5/29 16:49
 * @Version 1.0
 */
public enum QuartzJobEnum {

    TASK_JOB("testJob", "测试任务");

    private String code;
    private String desc;

    QuartzJobEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
