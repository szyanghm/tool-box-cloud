package com.tool.box.config;

import com.alibaba.fastjson.JSONObject;
import com.tool.box.service.ISysDictDataService;
import com.tool.box.utils.RedisUtils;
import com.tool.box.vo.SysDictDataVO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
    private ISysDictDataService sysDictDataService;

    /**
     * 字典数据缓存
     */
    public void initDictData() {
        Map<String, List<SysDictDataVO>> map = sysDictDataService.getDictMap();
        for (String str : map.keySet()) {
            redisUtils.set(str, JSONObject.toJSONString(map.get(str)));
        }
    }

    /**
     * 初始化加载数据方法
     */
    @PostConstruct
    public void init() {
        initDictData();
    }
}
