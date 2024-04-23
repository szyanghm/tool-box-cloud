package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.model.GoodsInfo;
import com.tool.box.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品信息表 服务类
 *
 * @author v_haimiyang
 * @since 2024-04-18
 */
public interface IGoodsInfoService extends IService<GoodsInfo> {

    /**
     * 新增商品
     *
     * @param goodsInfo 商品信息
     * @param files     商品附件
     * @return 上传结果
     */
    ResultVO add(MultipartFile[] files, String goodsInfo);
}
