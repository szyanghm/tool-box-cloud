package com.tool.box.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.common.Contents;
import com.tool.box.common.DictValue;
import com.tool.box.mapper.DictDataMapper;
import com.tool.box.model.DictData;
import com.tool.box.service.IDictDataService;
import com.tool.box.utils.RedisUtils;
import com.tool.box.vo.DictDataVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据表 服务实现类
 *
 * @author v_haimiyang
 * @since 2024-03-04
 */
@Service
public class DictDataServiceImpl
        extends ServiceImpl<DictDataMapper, DictData>
        implements IDictDataService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String, List<DictDataVO>> getDictMap() {
        Map<String, List<DictDataVO>> map = new HashMap<>();
        List<DictData> list = this.list(new QueryWrapper<DictData>().lambda()
                .eq(DictData::getIsDefault, Contents.IS_Y)
                .eq(DictData::getStatus, Contents.NUM_0)
        );
        if (CollectionUtil.isEmpty(list)) {
            return map;
        }
        List<DictDataVO> voList = new ArrayList<>();
        for (DictData dictData : list) {
            DictDataVO vo = new DictDataVO();
            BeanUtils.copyProperties(dictData, vo);
            voList.add(vo);
        }
        map = voList.stream().collect(Collectors.groupingBy(DictDataVO::getDictType));
        return map;
    }

    @Override
    public List<DictDataVO> getDictData(String dictType) {
        return redisUtils.range(dictType);
    }

    @Override
    public DictValue getDict(String dictType, String dictValue) {
        LambdaQueryWrapper<DictData> queryWrapper = new QueryWrapper<DictData>().lambda();
        queryWrapper.eq(DictData::getDictType, dictType);
        if (StringUtils.isNotBlank(dictValue)) {
            queryWrapper.eq(DictData::getDictValue, dictValue);
        }
        queryWrapper.last(" limit 1");
        DictData dictData = baseMapper.selectOne(queryWrapper);
        DictValue data = new DictValue();
        if (dictData != null) {
            data.setDictLabel(dictData.getDictLabel());
            data.setDictValue(dictData.getDictValue());
        }
        return data;
    }
}
