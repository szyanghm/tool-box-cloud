package com.tool.box.controller;

import com.tool.box.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping(value = "/post")
    public void test() {
        try {

//            Object object = ApplicationContextUtils.getBean("TestController");
//            System.out.println(object.getClass().getName());
            Class<?> clazz = Class.forName("com.tool.box.quartz.QuartzJobFactory");

            //获取首字母小写类名
//            String simpleName = clazz.getSimpleName();
//            String firstLowerName = simpleName.substring(0, 1).toLowerCase()
//                    + simpleName.substring(1);
            Object bean = ApplicationContextUtils.getBean(clazz);
            System.out.println(bean.getClass().getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        log.info("1111111111111111111");
    }



}
