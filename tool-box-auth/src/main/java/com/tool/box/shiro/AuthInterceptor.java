package com.tool.box.shiro;

import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.common.Contents;
import com.tool.box.common.DataStatic;
import com.tool.box.config.SystemConfig;
import com.tool.box.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author v_haimiyang
 * @Date 2024/3/28 11:07
 * @Version 1.0
 */
@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private SystemConfig systemConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //获取token
        String token = request.getHeader(Contents.X_ACCESS_TOKEN);
        //获取当前运行环境
        String profile = systemConfig.getEnvironment().getRequiredProperty(Contents.PROFILE);
        if (!systemConfig.enabled && DataStatic.profileDataList.contains(profile)
                && StringUtils.isBlank(token)) {
            token = tokenUtils.getAuthToken(Contents.ADMIN);
        }
        if (StringUtils.isNotBlank(token)) {
            LoginUser user = tokenUtils.checkJwtTokenRefresh(token);
            LocalProvider.init(request, response, user);
        }
        return true;
    }

}
