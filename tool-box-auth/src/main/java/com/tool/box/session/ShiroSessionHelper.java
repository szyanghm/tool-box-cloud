package com.tool.box.session;

import com.tool.box.config.BaseToken;
import org.apache.shiro.SecurityUtils;

/**
 * ShiroSession帮助类
 *
 * @Author v_haimiyang
 * @Date 2023/6/13 14:57
 * @Version 1.0
 */
public class ShiroSessionHelper {

    /**
     * 设置session中的channel信息
     *
     * @param token 登录token封装
     */
    public static void setChannel(BaseToken token) {
        String channel = token.getChannel();
        SecurityUtils.getSubject().getSession().setAttribute("channel", channel);
    }

    /**
     * 获取session中设置的channel信息
     *
     * @return channel信息
     */
    public static String getChannel() {
        return (String) SecurityUtils.getSubject().getSession().getAttribute("channel");
    }
}
