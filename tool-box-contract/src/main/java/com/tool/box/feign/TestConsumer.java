package com.tool.box.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * tool-box-service服务：feign客户端
 *
 * @Author v_haimiyang
 * @Date 2023/5/24 17:37
 * @Version 1.0
 */
@FeignClient(value = "tool-box-service", path = "/test")
public interface TestConsumer {

    @PostMapping(value = "/post")
    void test();

}
