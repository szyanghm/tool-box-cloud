package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 权限表
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Getter
@Setter
@ToString
@TableName("t_permissions")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String permissionsCode;

    private String permissionsName;

}
