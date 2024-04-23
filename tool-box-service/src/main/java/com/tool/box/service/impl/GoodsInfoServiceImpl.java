package com.tool.box.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.bo.MallCarouselBO;
import com.tool.box.bo.OssFileBO;
import com.tool.box.common.Contents;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.mapper.GoodsInfoMapper;
import com.tool.box.model.GoodsInfo;
import com.tool.box.service.IGoodsInfoService;
import com.tool.box.service.IMallCarouselService;
import com.tool.box.service.IOssFileService;
import com.tool.box.vo.OssFileVO;
import com.tool.box.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品信息表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-04-18
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

    @Resource
    private OssFileConsumer ossFileConsumer;
    @Resource
    private IOssFileService ossFileService;
    @Resource
    private IMallCarouselService mallCarouselService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO add(MultipartFile[] files, String goodsInfo) {
        //商品信息
        LoginUser user = LocalProvider.getUser();
        GoodsInfo info = JSONObject.parseObject(goodsInfo, GoodsInfo.class);
        String carouselKey = user.getAccount() + Contents.CAROUSEL_KEY + IdUtil.fastSimpleUUID();
        MallCarouselBO bo = new MallCarouselBO();
        if (info.getId() != null) {
            GoodsInfo backInfo = baseMapper.selectById(info.getId());
            if (ObjectUtil.isNotNull(backInfo)) {
                bo.setBackCarouselKey(backInfo.getCarouselKey());
            }
        }
        bo.setCarouselKey(carouselKey);
        ResultVO<List<OssFileVO>> resultVO = ossFileConsumer.uploads(files);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        List<OssFileVO> voList = resultVO.getData();
        List<String> fileKeys = voList.stream().map(OssFileVO::getFileKey).collect(Collectors.toList());
        bo.setFileKeys(fileKeys);
        info.setCarouselKey(carouselKey);
        this.saveOrUpdate(info);
        List<String> backFileKeys = mallCarouselService.add(bo);
        OssFileBO fileBO = OssFileBO.builder().list(voList).backFileKeys(backFileKeys).build();
        ossFileService.updateFiles(fileBO);
        return ResultVO.success();
    }
}
