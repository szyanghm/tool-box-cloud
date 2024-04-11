package com.tool.box.feign.result;

import com.tool.box.common.SystemUrl;
import com.tool.box.decode.NotBreakerConfiguration;
import com.tool.box.dto.LoginDTO;
import com.tool.box.dto.UserRegisterDTO;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * tool-box-service服务：feign客户端(不脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/8/14 18:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "notBreaker-validation"
        , configuration = NotBreakerConfiguration.class)
public interface LoginConsumer {

    @PostMapping(value = SystemUrl.login_url)
    ResultVO<?> login(@RequestBody LoginDTO dto);

    /**
     * 退出登录
     *
     * @return 登出结果
     */
    @RequestMapping(value = SystemUrl.logout_url)
    ResultVO<Object> logout();

    @PostMapping(value = SystemUrl.register_url)
    ResultVO<?> register(@RequestBody @Validated UserRegisterDTO dto);

}
