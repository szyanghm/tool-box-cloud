package com.tool.box.controller;

import com.tool.box.utils.FileUtils;
import com.tool.box.utils.MailUtils;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Author v_haimiyang
 * @Date 2024/3/20 9:37
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/mail")
public class MailController {
    @Resource
    private MailUtils mailUtils;

    @PostMapping(value = "/send")
    public ResultVO<?> send() {
        File file = new File("D:\\my_project\\project\\tool-box-cloud\\tool-box-service\\src\\main\\resources\\static\\checkOut.png");
        String str = FileUtils.imageToBase64(file);
        str = "<img src='data:image;base64," + str +"'/>";
        mailUtils.sendMessageHTML("szyanghaiming@163.com", "邮件主题", str);
        return ResultVO.success();
    }
}
