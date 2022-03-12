package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.AdminService;
import com.hu.myblog.admin.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/userInfo")
    public Result userInfo(Authentication authentication) {

        return Result.ok(authentication.getName());
    }

    @PostMapping("/userList")
    public Result permissionList(@RequestBody PageParam pageParam) {
        Page<AdminVo> adminVoPage = adminService.findAdminVoPage(pageParam);
        return Result.ok(adminVoPage);
    }


}
