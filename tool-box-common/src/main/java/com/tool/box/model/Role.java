package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色表
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleCode;

    private String roleName;

}
