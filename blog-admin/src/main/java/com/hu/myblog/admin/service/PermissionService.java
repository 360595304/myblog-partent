package com.hu.myblog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.modle.params.PageParam;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
public interface PermissionService extends IService<Permission> {

    Page<Permission> getPermissionPage(PageParam pageParam);
}
