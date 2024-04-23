package com.tool.box.exception;

import com.tool.box.enums.SystemCodeEnum;
import lombok.Getter;

/**
 * @Author v_haimiyang
 * @Date 2023/6/28 15:22
 * @Version 1.0
 */
@Getter
public class InternalApiException extends RuntimeException {

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常信息(和responseVo字段msg名称一致,避免丢失异常信息)
     */
    private String message;

    public InternalApiException(SystemCodeEnum systemCodeEnum) {
        super(systemCodeEnum.getMessage());
        this.code = systemCodeEnum.getCode();
        this.message = systemCodeEnum.getMessage();
    }


    public InternalApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
