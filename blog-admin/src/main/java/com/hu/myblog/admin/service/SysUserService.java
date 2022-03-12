package com.hu.myblog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.SysUser;
import com.hu.myblog.admin.modle.params.PageParam;

public interface SysUserService extends IService<SysUser> {
    SysUser findUser(String username, String password);

    SysUser findUserByUsername(String username);

    Page<SysUser> getSysUserPage(PageParam pageParam);
}
