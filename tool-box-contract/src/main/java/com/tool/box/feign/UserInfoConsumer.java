package com.tool.box.feign;

import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.decode.DecodeConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * tool-box-service服务：feign客户端(自动脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/5/24 17:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "decode-validation", path = "/user"
        , configuration = DecodeConfiguration.class)
public interface UserInfoConsumer {

    @PostMapping(value = "/getLoginUser")
    LoginUser getLoginUser(@RequestParam("account") String account);


    @PostMapping(value = "/getUserInfo")
    UserInfo getUserInfo(@RequestParam("account") String account);

    @PostMapping(value = "/getPermissions")
    List<String> getPermissions(@RequestParam("role") String role);

    /**
     * 根据用户账号查询密码
     *
     * @param account 用户账号
     * @return 密码
     */
    @PostMapping(value = "/getPassword")
    String getPassword(@RequestParam("account") String account);
}
