package com.tool.box.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tool.box.common.Contents;
import com.tool.box.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户信息-用于系统业务使用
 *
 * @Author v_haimiyang
 * @Date 2023/6/26 17:11
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {


    @ApiModelProperty("账号(唯一)")
    private String account;

    @ApiModelProperty("角色")
    private String roleCode;

    @ApiModelProperty("姓名")
    private String fullName;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = DateUtils.DEFAULT_DATE_PATTERN, timezone = Contents.GMT8)
    private Date birthday;

    @ApiModelProperty("身高(单位cm)")
    private Integer height;

    @ApiModelProperty("手机号码")
    private String phone;

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
