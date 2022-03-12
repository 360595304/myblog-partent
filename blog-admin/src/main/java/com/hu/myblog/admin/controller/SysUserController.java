package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.SysUser;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/userList")
    public Result userList(@RequestBody PageParam pageParam) {
        Page<SysUser> userPage = userService.getSysUserPage(pageParam);
        return Result.ok(userPage);
    }
}
