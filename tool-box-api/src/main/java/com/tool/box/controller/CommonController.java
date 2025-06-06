package com.tool.box.controller;

import com.tool.box.common.SystemUrl;
import com.tool.box.feign.result.DictConsumer;
import com.tool.box.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 公用接口
 *
 * @Author v_haimiyang
 * @Date 2024/6/17 16:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private DictConsumer dictConsumer;

    @GetMapping(value = SystemUrl.getDictData)
    public ResultVO<?> getDictData(@RequestParam("dictType") String dictType) {
        return dictConsumer.getDictData(dictType);
    }
}
