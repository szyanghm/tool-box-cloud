package com.tool.box.controller;

import com.tool.box.dto.QuartzJobDTO;
import com.tool.box.dto.UserRegisterDTO;
import com.tool.box.feign.result.LoginConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 定时任务控制器
 *
 * @Author v_haimiyang
 * @Date 2023/12/29 16:24
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    public static int num = 0;

    @Resource
    private LoginConsumer loginConsumer;

    @PostMapping(value = "/test")
    public ResultVO<?> test(@RequestBody QuartzJobDTO dto) {
        UserRegisterDTO dto1 = new UserRegisterDTO();
        dto1.setAccount("a456789");
        dto1.setPhone("13590442011");
        dto1.setPassword("123456789");
        num = num + 1;
        System.out.println("并发计数器:" + num);
        loginConsumer.register(dto1);
        log.info("1111111111111111111,{}", dto.getJobGroup());
        //   throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY);
        return ResultVO.success();
    }
}
