package com.tool.box.controller;

import com.tool.box.service.IGoodsInfoService;
import com.tool.box.vo.ResultVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 商品信息表 前端控制器
 *
 * @author v_haimiyang
 * @since 2024-04-18
 */
@RestController
@RequestMapping("/goodsInfo")
public class GoodsInfoController {

    @Resource
    private IGoodsInfoService goodsInfoService;


    @RequestMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO add(@RequestPart("files") MultipartFile[] files, @RequestParam("goodsInfo") String goodsInfo) {
        return goodsInfoService.add(files, goodsInfo);
    }
}
