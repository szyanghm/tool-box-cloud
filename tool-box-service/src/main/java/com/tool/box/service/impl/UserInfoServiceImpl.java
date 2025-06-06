package com.tool.box.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tool.box.base.LoginUser;
import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.common.validata.LoginLimit;
import com.tool.box.dto.LoginDTO;
import com.tool.box.dto.UserRegisterDTO;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.feign.OssFileConsumer;
import com.tool.box.jwt.JwtToken;
import com.tool.box.model.User;
import com.tool.box.service.IUserDetailService;
import com.tool.box.service.IUserInfoService;
import com.tool.box.service.IUserService;
import com.tool.box.utils.*;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Slf4j
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
        UserInfo userInfo = redisUtils.get(Contents.USERINFO + account);
        if (ObjectUtil.isNull(userInfo)) {
            userInfo = userDetailService.getByAccount(account);
            ResultVO<String> resultVO = ossFileConsumer.getUrl(userInfo.getUserAvatar());
            if (resultVO.isSuccess()) {
                userInfo.setUserAvatar(resultVO.getData());
            }
            redisUtils.set(Contents.USERINFO + account, userInfo);
        }
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<?> register(UserRegisterDTO dto) {
        userService.update(null,new LambdaUpdateWrapper<User>()
                .setSql("status = status + 1" )
                .eq(User::getAccount, "a456789")
        );
        List<String> phones = redisUtils.range("user_phone");
        List<String> accounts = redisUtils.range("user_account");
        if (accounts.contains(dto.getAccount())) {
            return ResultVO.error(SystemCodeEnum.ACCOUNT_ALREADY_EXISTS);
        }
        User user = new User();
        if (StringUtils.isNotBlank(dto.getPhone())) {
            if (phones.contains(dto.getPhone())) {
                return ResultVO.error(SystemCodeEnum.USER_PHONE_USED);
            }
            if (!dto.getPhone().matches(RegexpUtils.R_PHONE)) {
                return ResultVO.error(SystemCodeEnum.USER_PHONE_ERROR);
            }
            user.setPhone(dto.getPhone());
        }
        user.setAccount(dto.getAccount());
        user.setCreateBy("system");
        user.setUpdateBy("system");
        String salt = IdUtil.fastSimpleUUID();
        String password = new SimpleHash(Sha256Hash.ALGORITHM_NAME, dto.getPassword()
                , ByteSource.Util.bytes(salt)
                , 1024).toBase64();
        user.setSalt(salt);
        user.setPassword(password);
        user.setRoleCode("USER");
        if (userService.save(user)) {
            ResultVO.success();
        }
        return ResultVO.fail();
    }

    @Override
    @LoginLimit
    public ResultVO<?> login(LoginDTO dto) {
        User user = userService.getByAccount(dto.getAccount());
        if (user == null) {
            throw new InternalApiException(SystemCodeEnum.USER_DOES_NOT_EXIST);
        }
        if (user.getStatus() == 1) {
            throw new InternalApiException(SystemCodeEnum.USER_LOCK_ING);
        }
        String password = new SimpleHash(Sha256Hash.ALGORITHM_NAME, dto.getPassword()
                , ByteSource.Util.bytes(user.getSalt())
                , 1024).toBase64();
        if (!user.getPassword().equals(password)) {
            throw new InternalApiException(SystemCodeEnum.PASSWORD_ERROR);
        }
        LoginUser loginUser = SystemUtils.getUserInfo(user);
        String token = JwtUtils.createToken(loginUser);
        // 设置超时时间
        tokenUtils.setToken(token, loginUser.getAccount());
        SecurityUtils.getSubject().login(new JwtToken(token));
        log.info("用户账号:" + dto.getAccount() + "登录成功");
        return ResultVO.success(token);
    }

}
