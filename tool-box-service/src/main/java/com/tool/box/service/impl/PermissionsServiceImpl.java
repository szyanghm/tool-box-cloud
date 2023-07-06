package com.tool.box.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.mapper.PermissionsMapper;
import com.tool.box.model.Permissions;
import com.tool.box.service.IPermissionsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限表 服务实现类
 *
 * @author v_haimiyang
 * @since 2023-06-28
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

    @Override
    public Set<String> getPermissions(String role) {
        List<String> list = baseMapper.getPermissions(role);
        if (list == null || list.size() == 0) {
            throw new InternalApiException(SystemCodeEnum.USER_NOT_PERMISSIONS);
        }
        return new HashSet<>(list);
    }

}
