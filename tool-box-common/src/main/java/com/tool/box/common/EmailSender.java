package com.tool.box.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮件字典json对象
 *
 * @Author v_haimiyang
 * @Date 2024/3/19 15:47
 * @Version 1.0
 */
@Data
public class EmailSender {

    @ApiModelProperty(value = "邮件服务器域名")
    private String host;
    @ApiModelProperty(value = "端口")
    private int port = -1;
    @ApiModelProperty(value = "发件方邮箱")
    private String username;
    @ApiModelProperty(value = "发件方授权码")
    private String password;
}
