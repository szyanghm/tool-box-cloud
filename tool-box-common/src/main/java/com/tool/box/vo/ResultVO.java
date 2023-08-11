package com.tool.box.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tool.box.common.ErrorType;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 公共返回
 *
 * @Author v_haimiyang
 * @Date 2023/6/28 14:28
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultVO<T> {

    public static final Integer SUCCESSFUL_CODE = 2000;
    public static final String SUCCESSFUL_MSG = "处理成功";

    private Integer code;
    private String msg;
    private String time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResultVO(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMsg();
        this.time = DateUtils.getCurrentDateTime();
    }

//    public ResultVO(ErrorType errorType, T data) {
//        this.code = errorType.getCode();
//        this.msg = errorType.getMsg();
//        this.data = data;
//        this.time = DateUtils.getCurrentDateTime();
//    }


    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = DateUtils.getCurrentDateTime();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     */
    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
    public static ResultVO success(Object data) {
        return new ResultVO<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }


    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static ResultVO fail() {
        return new ResultVO(SystemCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static ResultVO error(SystemCodeEnum errorEnum) {
        return new ResultVO(errorEnum);
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
