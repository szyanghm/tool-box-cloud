package com.tool.box.config;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.common.InitUser;
import com.tool.box.service.IDictDataService;
import com.tool.box.service.IUserService;
import com.tool.box.utils.RedisUtils;
import com.tool.box.vo.DictDataVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据初始化配置
 *
 * @Author v_haimiyang
 * @Date 2024/3/19 17:08
 * @Version 1.0
 */
@Component
public class DataInitConfig {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IDictDataService sysDictDataService;
    @Resource
    private IUserService userService;

    /**
     * 字典数据缓存
     */
    public void initDictData() {
        Map<String, List<DictDataVO>> map = sysDictDataService.getDictMap();
        for (String str : map.keySet()) {
            redisUtils.set(str, JSONObject.toJSONString(map.get(str)));
        }
    }

    public void initUser() {
        List<InitUser> list = userService.initUser();
        List<String> phones = new ArrayList<>();
        List<String> accounts = new ArrayList<>();
        for (InitUser initUser : list) {
            accounts.add(initUser.getAccount());
            if (StringUtils.isNotBlank(initUser.getPhone())) {
                phones.add(initUser.getPhone());
            }
        }
        redisUtils.leftPushAllStr("user_phone", phones.toArray(new String[0]));
        redisUtils.leftPushAllStr("user_account", accounts.toArray(new String[0]));
    }

    /**
     * 初始化加载数据方法
     */
    @PostConstruct
    public void init() {
        initDictData();
        initUser();
    }
}
