package com.tool.box.controller;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.feign.result.GoodsInfoConsumer;
import com.tool.box.model.GoodsInfo;
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
    private GoodsInfoConsumer goodsInfoConsumer;


    @RequestMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO add(@RequestPart("files") MultipartFile[] files, @RequestParam("goodsInfo") String goodsInfo) {
        return goodsInfoConsumer.add(files, goodsInfo);
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setGoodsIntro("商品简介");
        goodsInfo.setGoodsName("衣服");
        goodsInfo.setCategoryId(1L);
        goodsInfo.setCostPrice(500);
        goodsInfo.setSellingPrice(600);
        goodsInfo.setOriginalPrice(700);
        goodsInfo.setStockNum(10);
        goodsInfo.setStatus("GOODS_UP");
        System.out.println(JSONObject.toJSONString(goodsInfo));
    }
}
