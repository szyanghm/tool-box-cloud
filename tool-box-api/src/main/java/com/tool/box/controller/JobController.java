package com.tool.box.controller;

import com.tool.box.dto.QuartzJobDTO;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/test")
    public ResultVO<?> test(@RequestBody QuartzJobDTO dto) {
        log.info("1111111111111111111,{}", dto.getJobGroup());
        //   throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY);
        return ResultVO.success();
    }
}
