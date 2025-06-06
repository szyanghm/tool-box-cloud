package com.tool.box.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tool.box.common.Contents;
import com.tool.box.common.validata.DataMasking;
import com.tool.box.enums.DataMaskingTypeEnum;
import com.tool.box.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
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


    @Schema(description = "账号(唯一)")
    private String account;

    @Schema(description = "角色")
    private String roleCode;

    @Schema(description = "姓名")
    private String fullName;

    @Schema(description = "用户头像")
    private String userAvatar;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "出生日期")
    @JsonFormat(pattern = DateUtils.DEFAULT_DATE_PATTERN, timezone = Contents.GMT8)
    private Date birthday;

    @Schema(description = "身高(单位cm)")
    private Integer height;

    @Schema(description = "手机号码")
    @DataMasking(type = DataMaskingTypeEnum.MOBILE_PHONE)
    private String phone;

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
