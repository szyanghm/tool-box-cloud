package com.tool.box.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于缓存账户、手机号
 *
 * @Author v_haimiyang
 * @Date 2024/4/3 11:18
 * @Version 1.0
 */
@Data
public class InitUser {

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("手机号码")
    private String phone;

}
