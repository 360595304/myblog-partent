package com.hu.myblog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Admin;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.Role;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.vo.AdminVo;

import java.util.List;

public interface AdminService extends IService<Admin> {
    Admin findAdminByUsername(String username);

    List<Permission> findPermissionByRoleId(Long id);

    List<Role> findRoleByAdminId(Long id);

    Page<AdminVo> findAdminVoPage(PageParam pageParam);
}
