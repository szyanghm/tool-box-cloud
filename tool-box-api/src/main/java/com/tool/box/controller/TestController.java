package com.tool.box.controller;

import com.tool.box.feign.TestConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试-控制器
 *
 * @Author v_haimiyang
 * @Date 2023/4/20 15:53
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestConsumer testConsumer;

    @PostMapping(value = "/post")
    public void test() {
        log.info("1111111111111111111");
        testConsumer.test();
    }

}
