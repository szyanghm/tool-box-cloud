package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@ApiModel(value = "UserDetail对象", description = "用户信息表")
public class UserDetail extends BaseModel<UserDetail> {

    @ApiModelProperty("账号(唯一)")
    private String account;

    @ApiModelProperty("姓名")
    private String fullName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("出生日期")
    private Integer birthday;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("身高(单位cm)")
    private Integer height;

    @ApiModelProperty("是否有房(N否/Y是)")
    private String isHouse;

    @ApiModelProperty("是否有车(N否/Y是)")
    private String isCar;

    @ApiModelProperty("微信号")
    private String weChat;

    @ApiModelProperty("家庭地址")
    private String homeAddress;

    @ApiModelProperty("工作地址")
    private String officeAddress;

}
