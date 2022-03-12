package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.mapper.PermissionMapper;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Page<Permission> getPermissionPage(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(pageParam.getQueryString())) {
            queryWrapper.like(Permission::getName, pageParam.getQueryString());
        }
        return permissionMapper.selectPage(page, queryWrapper);
    }
}
