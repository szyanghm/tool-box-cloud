package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.base.UserInfo;
import com.tool.box.model.UserDetail;
import com.tool.box.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author v_haimiyang
 * @since 2024-02-29
 */
public interface IUserDetailService extends IService<UserDetail> {

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return 用户信息
     */
    UserInfo getByAccount(String account);

    /**
     * 上传用户头像
     *
     * @param file 附件
     * @return 上传结果
     */
    ResultVO updateUserAvatar(MultipartFile file);
}
