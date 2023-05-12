package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 测试表
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@TableName("t_test")
//@ApiModel(value = "Test对象", description = "测试表")
public class Test extends BaseModel<Test> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test{" +
            "name=" + name +
        "}";
    }
}
