package com.tool.box.feign;

import com.tool.box.base.LoginUser;
import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * tool-box-service服务：feign客户端
 *
 * @Author v_haimiyang
 * @Date 2023/5/24 17:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", path = "/user", configuration = NotBreakerConfiguration.class)
public interface UserInfoConsumer {

    @PostMapping(value = "/getLoginUser")
    LoginUser getLoginUser(@RequestParam("account") String account);

    @PostMapping(value = "/getPermissions")
    Set<String> getPermissions(@RequestParam("role") String role);
}
