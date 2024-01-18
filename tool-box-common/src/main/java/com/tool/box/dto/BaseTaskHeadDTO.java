package com.tool.box.dto;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 任务主体
 *
 * @Author v_haimiyang
 * @Date 2023/12/29 15:43
 * @Version 1.0
 */
@Data
public class BaseTaskHeadDTO {

    /**
     * 任务实体类
     */
    private Object bean;
    /**
     * 任务实体类-方法
     */
    private Method targetMethod;
    /**
     * 任务实体类方法-参数对象
     */
    private Class<?> paramClass;
}
