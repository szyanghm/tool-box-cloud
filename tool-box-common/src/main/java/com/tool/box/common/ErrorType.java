package com.tool.box.common;

/**
 * 状态码接口
 *
 * @Author v_haimiyang
 * @Date 2023/6/28 14:34
 * @Version 1.0
 */
public interface ErrorType {

    /**
     * 返回code
     *
     * @return
     */
    Integer getCode();

    /**
     * 返回mesg
     *
     * @return
     */
    String getMsg();
}
