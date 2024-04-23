package com.tool.box.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.mapper.UserDetailMapper;
import com.tool.box.model.UserDetail;
import com.tool.box.service.IOssFileService;
import com.tool.box.service.IUserDetailService;
import com.tool.box.vo.OssFileVO;
import com.tool.box.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用户信息表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements IUserDetailService {

    @Resource
    private OssFileConsumer ossFileConsumer;
    @Resource
    private IOssFileService ossFileService;

    @Override
    public UserInfo getByAccount(String account) {
        return baseMapper.getByAccount(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateUserAvatar(MultipartFile file) {
        ResultVO<OssFileVO> resultVO = ossFileConsumer.upload(file);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        OssFileVO vo = resultVO.getData();
        LoginUser loginUser = LocalProvider.getUser();
        String backFileKey = "";
        UserDetail userDetail = baseMapper.selectOne(new QueryWrapper<UserDetail>().lambda()
                .eq(UserDetail::getAccount, loginUser.getAccount())
        );
        if (ObjectUtil.isNotNull(userDetail)) {
            backFileKey = userDetail.getFileKey();
            userDetail.setFileKey(vo.getFileKey());
            baseMapper.updateById(userDetail);
        }
        ossFileService.updateFile(vo, backFileKey);
        return ResultVO.success();
    }
}
