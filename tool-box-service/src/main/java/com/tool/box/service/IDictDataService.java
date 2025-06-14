package com.tool.box.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tool.box.common.DictValue;
import com.tool.box.model.DictData;
import com.tool.box.vo.DictDataVO;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表 服务类
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
public interface IDictDataService extends IService<DictData> {

    /**
     * 字典数据缓存
     *
     * @return 字典数据
     */
    Map<String, List<DictDataVO>> getDictMap();

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    List<DictDataVO> getDictData(String dictType);


    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典数据
     */
    DictValue getDict(String dictType, String dictValue);

}
