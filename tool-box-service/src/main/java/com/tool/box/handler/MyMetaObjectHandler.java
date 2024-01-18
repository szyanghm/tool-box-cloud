package com.tool.box.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tool.box.base.UserInfo;
import com.tool.box.common.Contents;
import com.tool.box.common.LocalProvider;
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
    public UserInfo getUser() {
        UserInfo userInfo = LocalProvider.getUser();
        if (ObjectUtil.isEmpty(userInfo)) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

    /**
     * 新增保存时填充
     *
     * @param metaObject 被填充对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        UserInfo userInfo = getUser();
        log.info("start insert fill ....");
        // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, userInfo.getAccount());
        this.strictInsertFill(metaObject, "updateBy", String.class, userInfo.getAccount());
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
        UserInfo userInfo = getUser();
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "createBy", String.class, userInfo.getAccount());
        this.strictUpdateFill(metaObject, "updateBy", String.class, userInfo.getAccount());
        log.info("end update fill ....");
    }
}
