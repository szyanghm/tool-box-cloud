package com.tool.box.common;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "账号")
    private String account;
    @Schema(description = "手机号码")
    private String phone;

}
