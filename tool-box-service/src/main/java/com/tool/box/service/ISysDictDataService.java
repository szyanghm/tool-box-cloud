package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.common.DictData;
import com.tool.box.model.SysDictData;
import com.tool.box.vo.SysDictDataVO;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表 服务类
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 字典数据缓存
     *
     * @return 字典数据
     */
    Map<String, List<SysDictDataVO>> getDictMap();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    List<DictData> getDict(String dictType);


    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    DictData getDict(String dictType, String dictValue);

}
