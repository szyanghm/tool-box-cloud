package com.tool.box.controller;

import cn.hutool.http.HttpRequest;
import com.tool.box.dto.LoginDTO;
import com.tool.box.feign.AuthConsumer;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class TestController {

    @Resource
    private AuthConsumer authConsumer;

    @PostMapping(value = "/login")
    public ResultVO login(@RequestBody LoginDTO dto) {
        log.info("1111111111111111111");
        return authConsumer.login(dto);
    }

    @PostMapping(value = "/api")
    public ResultVO api() {
        return authConsumer.api();
    }

    public static void main(String[] args) {
        //链式构建请求
        String result2 = HttpRequest.post("localhost:19081/api")
                .header("token", "B547E0B832FBCBAB3D1E5D6A7AD60C07")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println(result2);
    }

}
