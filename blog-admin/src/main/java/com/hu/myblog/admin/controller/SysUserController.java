package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Permission;
import com.hu.myblog.admin.entity.SysUser;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.ErrorCode;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.OSSService;
import com.hu.myblog.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private OSSService ossService;

    @PostMapping("/userList")
    public Result userList(@RequestBody PageParam pageParam) {
        Page<SysUser> userPage = userService.getSysUserPage(pageParam);
        return Result.ok(userPage);
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysUser sysUser) {
        userService.updateById(sysUser);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String path = ossService.uploadFile(file, "head");
        if (StringUtils.isEmpty(path)) {
            return Result.fail(ErrorCode.UPLOAD_ERROR);
        }
        return Result.ok(path);
    }
}
