package com.tool.box.feign.result;

import com.tool.box.base.UserInfo;
import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * tool-box-service服务：feign客户端(自动脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/5/24 17:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "decode-validation", path = "/user"
        , configuration = NotBreakerConfiguration.class)
public interface UserInfoConsumer {


    @PostMapping(value = "/getUserInfo")
    ResultVO<UserInfo> getUserInfo(@RequestParam("account") String account);

}
