package com.tool.box.feign.result;

import com.tool.box.base.LoginUser;
import com.tool.box.decode.DecodeConfiguration;
import com.tool.box.decode.NotBreakerConfiguration;
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
@FeignClient(name = "tool-box-service", contextId = "decode-validation", path = "/permissions"
        , configuration = NotBreakerConfiguration.class)
public interface PermissionsConsumer {



    @PostMapping(value = "/getPermissions")
    List<String> getPermissions(@RequestParam("role") String role);

}
