package com.tool.box.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.dto.LoginDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.jwt.JwtToken;
import com.tool.box.model.User;
import com.tool.box.service.IUserDetailService;
import com.tool.box.service.IUserInfoService;
import com.tool.box.service.IUserService;
import com.tool.box.utils.JwtUtils;
import com.tool.box.utils.RedisUtils;
import com.tool.box.utils.SystemUtils;
import com.tool.box.utils.TokenUtils;
import com.tool.box.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 测试表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private IUserService userService;
    @Resource
    private IUserDetailService userDetailService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private OssFileConsumer ossFileConsumer;

    @Override
    public LoginUser getLoginUser(String account) {
        User user = userService.getByAccount(account);
        if (user == null) {
            throw new InternalApiException(SystemCodeEnum.USER_DOES_NOT_EXIST);
        }
        return SystemUtils.getUserInfo(user);
    }

    @Override
    public UserInfo getUserInfo(String account) {
        String str = redisUtils.get(account + Contents.USERINFO);
        UserInfo userInfo;
        if (StringUtils.isNotBlank(str)) {
            userInfo = JSONObject.parseObject(str, UserInfo.class);
        } else {
            userInfo = userDetailService.getByAccount(account);
            ResultVO<String> resultVO = ossFileConsumer.getUrl(userInfo.getUserAvatar());
            if (resultVO.isSuccess()) {
                userInfo.setUserAvatar(resultVO.getData());
            }
            redisUtils.set(account + Contents.USERINFO, JSONObject.toJSONString(userInfo));
        }
        return userInfo;
    }

    @Override
    public ResultVO<?> login(LoginDTO dto) {
        User user = userService.getByAccount(dto.getAccount());
        if (user == null) {
            return ResultVO.error(SystemCodeEnum.USER_DOES_NOT_EXIST);
        }
        if (user.getStatus() == 1) {
            return ResultVO.error(SystemCodeEnum.USER_LOCK_ING);
        }
        String password = new SimpleHash(Sha256Hash.ALGORITHM_NAME, dto.getPassword()
                , ByteSource.Util.bytes(user.getSalt())
                , 1024).toBase64();
        if (!user.getPassword().equals(password)) {
            return ResultVO.error(SystemCodeEnum.PASSWORD_ERROR);
        }
        LoginUser loginUser = SystemUtils.getUserInfo(user);
        String token = JwtUtils.createToken(loginUser);
        // 设置超时时间
        tokenUtils.setToken(token, loginUser.getAccount());
        SecurityUtils.getSubject().login(new JwtToken(token));
        return ResultVO.success(token);
    }

}
