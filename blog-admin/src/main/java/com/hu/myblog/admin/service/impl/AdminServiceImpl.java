package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Admin;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.Role;
import com.hu.myblog.admin.mapper.AdminMapper;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.service.AdminService;
import com.hu.myblog.admin.vo.AdminVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        queryWrapper.last("limit 1");
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Permission> findPermissionByRoleId(Long roleId) {
        return adminMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public List<Role> findRoleByAdminId(Long adminId) {
        return adminMapper.findRoleByAdminId(adminId);
    }

    @Override
    public Page<AdminVo> findAdminVoPage(PageParam pageParam) {
        Page<Admin> adminPage = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(pageParam.getQueryString())) {
            queryWrapper.like(Admin::getUsername, pageParam.getQueryString());
        }
        Page<Admin> page = adminMapper.selectPage(adminPage, queryWrapper);
        Page<AdminVo> userVoPage = new Page<>();
        userVoPage.setPages(page.getPages());
        userVoPage.setTotal(page.getTotal());
        userVoPage.setRecords(this.packUser(page.getRecords()));
        return userVoPage;
    }

    private List<AdminVo> packUser(List<Admin> records) {
        List<AdminVo> adminVoList = new ArrayList<>();
        records.forEach(user -> {
            AdminVo adminVo = new AdminVo();
            BeanUtils.copyProperties(user, adminVo);
            List<Role> roleList = this.findRoleByAdminId(user.getId());
            adminVo.setRoles(roleList);
            adminVoList.add(adminVo);
        });
        return adminVoList;
    }

}
