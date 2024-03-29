package com.tool.box.utils;


import com.tool.box.base.LoginUser;
import com.tool.box.common.Contents;
import com.tool.box.common.QuartzJobTask;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
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

    /**
     * 拼接JobKey的name
     *
     * @param job 任务对象
     * @return JobKey的name
     */
    public static String getGroupName(QuartzJobTask job) {
        StringBuilder sb = new StringBuilder();
        sb.append(job.getGroupName());
        sb.append("_");
        sb.append(job.getTaskId());
        return sb.toString();
    }

    public static String getMsg(SystemCodeEnum systemCodeEnum, String msg) {
        return systemCodeEnum.getMsg() + "==>>:" + msg;
    }

    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static String getErrorMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    log.info(e1.getMessage());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    /**
     * 根据AOP @Before的方法参数获取Method
     *
     * @param joinPoint @Before的方法参数
     * @return 方法对象
     */
    public static Method getCurrentMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    /**
     * 将User信息复制到UserInfo
     *
     * @param user 用户信息
     * @return UserInfo
     */
    public static LoginUser getUserInfo(User user) {
        LoginUser userInfo = new LoginUser();
        userInfo.setAccount(user.getAccount());
        userInfo.setRoleCode(user.getRoleCode());
        userInfo.setStatus(user.getStatus());
        return userInfo;
    }

    /**
     * 拼接错误信息
     *
     * @param msg      自定义消息
     * @param errorMsg 异常信息
     * @return 错误信息
     */
    public static String getErrorMsg(String msg, String errorMsg) {
        return msg + "\n" + errorMsg;
    }

}
