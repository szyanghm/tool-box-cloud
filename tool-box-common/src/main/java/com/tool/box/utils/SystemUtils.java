package com.tool.box.utils;


import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统工具类
 *
 * @Author v_haimiyang
 * @Date 2023/4/14 16:32
 * @Version 1.0
 */
@Slf4j
public class SystemUtils {

    /**
     * 获取系统环境变量配置
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getSystemProperty(String name, String defaultValue) {
        String value = System.getProperty(name);
        if (StringUtils.isBlank(value)) {
            value = System.getenv(name);
            if (StringUtils.isBlank(value)) {
                log.info("未读取到系统配置");
            }
        }
        return value;
    }

    /**
     * @param str   需分割的字符串
     * @param split 分隔符如：&和;
     * @return 分割后的map对象
     */
    public static Map<String, String> getMapData(Boolean enabled, String str, String split) {
        Map<String, String> dataMap = new LinkedHashMap<>(16);
        if (!enabled) {
            dataMap.put(Contents.SLASH_BIT2, Contents.anon);
            return dataMap;
        }
        if (str.contains(split)) {
            String[] arr = str.split(split);
            for (String s : arr) {
                String[] strArr = s.split(Contents.EQUALS_SIGN);
                dataMap.put(strArr[0], strArr[1]);
            }
        } else {
            String[] strArr = str.split(Contents.EQUALS_SIGN);
            dataMap.put(strArr[0], strArr[1]);
        }
        //放行swagger相关访问
        dataMap.put("/swagger-ui/**", Contents.anon);
        dataMap.put("/swagger-resources/**", Contents.anon);
        dataMap.put("/v3/**", Contents.anon);
        dataMap.put("/error/**", Contents.anon);
        dataMap.put(Contents.DRUID_SLASH_BIT2, Contents.anon);
        dataMap.put(Contents.SLASH_BIT2, Contents.authc);
        dataMap.put(Contents.SLASH_BIT2, Contents.JWT_FILTER);
        return dataMap;
    }

    public static String getMsg(SystemCodeEnum systemCodeEnum, String msg) {
        return systemCodeEnum.getMsg() + "==>>:" + msg;
    }

    /**
     * 将User信息复制到UserInfo
     *
     * @param user 用户信息
     * @return UserInfo
     */
    public static UserInfo getUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(user.getAccount());
        userInfo.setName(user.getName());
        userInfo.setRole(user.getRole());
        userInfo.setSalt(user.getSalt());
        userInfo.setStatus(user.getStatus());
        return userInfo;
    }


}
