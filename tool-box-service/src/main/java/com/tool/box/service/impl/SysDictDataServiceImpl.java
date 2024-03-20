package com.tool.box.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.common.Contents;
import com.tool.box.common.DictData;
import com.tool.box.mapper.SysDictDataMapper;
import com.tool.box.model.SysDictData;
import com.tool.box.service.ISysDictDataService;
import com.tool.box.vo.SysDictDataVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class SysDictDataServiceImpl
        extends ServiceImpl<SysDictDataMapper, SysDictData>
        implements ISysDictDataService {


    @Override
    public Map<String, List<SysDictDataVO>> getDictMap() {
        Map<String, List<SysDictDataVO>> map = new HashMap<>();
        List<SysDictData> list = this.list(new QueryWrapper<SysDictData>().lambda()
                .eq(SysDictData::getIsDefault, Contents.IS_Y)
                .eq(SysDictData::getStatus, Contents.NUM_0)
        );
        if (CollectionUtil.isEmpty(list)) {
            return map;
        }
        List<SysDictDataVO> voList = new ArrayList<>();
        for (SysDictData dictData : list) {
            SysDictDataVO vo = new SysDictDataVO();
            BeanUtils.copyProperties(dictData, vo);
            voList.add(vo);
        }
        map = voList.stream().collect(Collectors.groupingBy(SysDictDataVO::getDictType));
        return map;
    }

    @Override
    public List<DictData> getDict(String dictType) {
        List<SysDictData> list = baseMapper.selectList(new QueryWrapper<SysDictData>().lambda()
                .eq(SysDictData::getDictType, dictType)
        );
        List<DictData> dataList = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return dataList;
        }
        for (SysDictData dictData : list) {
            DictData data = new DictData();
            data.setDictLabel(dictData.getDictLabel());
            data.setDictValue(dictData.getDictValue());
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public DictData getDict(String dictType, String dictValue) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new QueryWrapper<SysDictData>().lambda();
        queryWrapper.eq(SysDictData::getDictType, dictType);
        if (StringUtils.isNotBlank(dictValue)) {
            queryWrapper.eq(SysDictData::getDictValue, dictValue);
        }
        queryWrapper.last(" limit 1");
        SysDictData dictData = baseMapper.selectOne(queryWrapper);
        DictData data = new DictData();
        if (dictData != null) {
            data.setDictLabel(dictData.getDictLabel());
            data.setDictValue(dictData.getDictValue());
        }
        return data;
    }
}
