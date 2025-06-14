package com.tool.box.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tool.box.common.ErrorType;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * 公共返回
 *
 * @Author v_haimiyang
 * @Date 2023/6/28 14:28
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ResultVO<T> implements Serializable {

    public static final Integer SUCCESSFUL_CODE = 2000;
    public static final String SUCCESSFUL_MSG = "处理成功";

    private Integer code;
    private String message;
    private String time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResultVO() {
        this.code = SUCCESSFUL_CODE;
        this.message = SUCCESSFUL_MSG;
        this.time = DateUtils.getCurrentDateTime();
    }

    public ResultVO(ErrorType errorType) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
        this.time = DateUtils.getCurrentDateTime();
    }

    public ResultVO(ErrorType errorType, T data) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
        this.data = data;
        this.time = DateUtils.getCurrentDateTime();
    }


    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param message
     * @param data
     */
    private ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = DateUtils.getCurrentDateTime();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param message
     */
    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.time = DateUtils.getCurrentDateTime();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @return Result
     */
    public static ResultVO success() {
        return new ResultVO<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG);
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<T>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }


    /**
     * 操作失败
     *
     * @return Result
     */
    public static <T> ResultVO<T> fail() {
        return new ResultVO(SystemCodeEnum.OPERATE_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static ResultVO error(SystemCodeEnum errorEnum) {
        return new ResultVO(errorEnum);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static <T> ResultVO<T> error(SystemCodeEnum errorEnum, T t) {
        return new ResultVO(errorEnum, t);
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, SystemCodeEnum.OK.getCode());
    }

    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
