package com.hu.myblog.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author suhu
 * @createDate 2022/3/9
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        SysUser user = userService.getUserByToken(token);
        if (user == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return Result.ok(userVo);
    }

    @GetMapping("/auth/test")
    public Result test() {
        SysUser user = UserThreadLocal.get();
        System.out.println(user);
        return Result.ok();
    }
}
