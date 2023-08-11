package com.tool.box.utils;


import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
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
    public static Map<String, String> getMapData(String str, String split) {
        String[] arr = str.split(split);
        Map<String, String> dataMap = new HashMap<>(16);
        for (int i = 0; i < arr.length; i++) {
            String[] strArr = arr[i].split("=");
            dataMap.put(strArr[0], strArr[1]);
        }
        return dataMap;
    }

    /**
     * 将LoginUser信息复制到UserInfo
     *
     * @param loginUser 登录用户信息
     * @return UserInfo
     */
    public static UserInfo getUserInfo(LoginUser loginUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setRole(loginUser.getRole());
        userInfo.setSalt(loginUser.getSalt());
        userInfo.setName(loginUser.getName());
        userInfo.setAccount(loginUser.getAccount());
        return userInfo;
    }

}
