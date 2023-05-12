package com.tool.box.controller;

import com.alibaba.fastjson2.JSONObject;
import com.tool.box.model.Test;
import com.tool.box.service.ITestService;
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
    private ITestService testService;

    @PostMapping(value = "/post")
    public void test() {
        Test test = testService.getById(1);
        String str = JSONObject.toJSONString(test);
        System.out.println(str);
    }




}
