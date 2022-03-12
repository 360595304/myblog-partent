package com.hu.myblog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.myblog.admin.entity.Admin;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    List<Permission> findPermissionByRoleId(Long roleId);

    List<Role> findRoleByAdminId(Long adminId);
}
