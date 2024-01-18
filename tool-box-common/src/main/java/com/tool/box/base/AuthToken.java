package com.tool.box.base;

import lombok.Data;

/**
 * redis存储的认证对象
 *
 * @Author v_haimiyang
 * @Date 2023/8/17 11:36
 * @Version 1.0
 */
@Data
public class AuthToken {

    private String account;
    private String token;

}
