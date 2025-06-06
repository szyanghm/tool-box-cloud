package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_user_detail")
@Tag(name = "UserDetail对象", description = "用户信息表")
public class UserDetail extends BaseModel<UserDetail> {

    @Schema(description = "账号(唯一)")
    private String account;

    @Schema(description = "姓名")
    private String fullName;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "出生日期")
    private Date birthday;

    @Schema(description = "用户头像，关联附件表file_key")
    private String fileKey;

    @Schema(description = "身高(单位cm)")
    private Integer height;

    @Schema(description = "是否有房(N否/Y是)")
    private String isHouse;

    @Schema(description = "是否有车(N否/Y是)")
    private String isCar;

    @Schema(description = "微信号")
    private String weChat;

    @Schema(description = "家庭地址")
    private String homeAddress;

    @Schema(description = "工作地址")
    private String officeAddress;

}
