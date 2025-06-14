package com.tool.box.controller;

import com.tool.box.common.SystemUrl;
import com.tool.box.service.IDictDataService;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 字典类型表 前端控制器
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
@Slf4j
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictDataService iDictDataService;


    @GetMapping(value = SystemUrl.getDictData)
    public ResultVO<?> getDictData(@RequestParam("dictType") String dictType) {
        return ResultVO.success(iDictDataService.getDictData(dictType));
    }

}
