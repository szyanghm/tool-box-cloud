package com.tool.box.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tool.box.base.LocalProvider;
import com.tool.box.base.LoginUser;
import com.tool.box.common.Contents;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus-新增/更新自动填充
 *
 * @Author v_haimiyang
 * @Date 2023/6/26 17:22
 * @Version 1.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 获取当前用户
     *
     * @return 用户信息
     */
    public LoginUser getUser() {
        LoginUser user = LocalProvider.getUser();
        if (ObjectUtil.isEmpty(user)) {
            user = new LoginUser();
        }
        return user;
    }

    /**
     * 新增保存时填充
     *
     * @param metaObject 被填充对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser user = getUser();
        log.info("start insert fill ....");
        // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, user.getAccount());
        this.strictInsertFill(metaObject, "updateBy", String.class, user.getAccount());
        this.strictInsertFill(metaObject, "isDelete", Integer.class, Contents.NUM_0);
        log.info("end insert fill ....");
    }

    /**
     * 更新时填充
     *
     * @param metaObject 被填充对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser user = getUser();
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "createdBy", String.class, user.getAccount());
        this.strictUpdateFill(metaObject, "updatedBy", String.class, user.getAccount());
        log.info("end update fill ....");
    }
}
