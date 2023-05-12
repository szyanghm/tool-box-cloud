package com.tool.box.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.mapper.TestMapper;
import com.tool.box.model.Test;
import com.tool.box.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试表 服务实现类
 * </p>
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
