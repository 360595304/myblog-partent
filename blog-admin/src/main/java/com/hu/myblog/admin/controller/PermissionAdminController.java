package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@RestController
@RequestMapping("/admin/permission")
public class PermissionAdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam) {
        Page<Permission> permissionPage = permissionService.getPermissionPage(pageParam);
        return Result.ok(permissionPage);
    }

    @PostMapping("/update")
    public Result permissionUpdate(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

    @PostMapping("/add")
    public Result permissionAdd(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result permissionDelete(@PathVariable Long id) {
        permissionService.removeById(id);
        return Result.ok();
    }

}
