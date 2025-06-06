package com.tool.box.common;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "邮件服务器域名")
    private String host;
    @Schema(description = "端口")
    private int port = -1;
    @Schema(description = "发件方邮箱")
    private String username;
    @Schema(description = "发件方授权码")
    private String password;
}
