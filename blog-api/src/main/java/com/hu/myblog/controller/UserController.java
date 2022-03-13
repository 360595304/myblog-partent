package com.hu.myblog.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.handler.MyException;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.UserVo;
import com.hu.myblog.vo.params.UserParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PostMapping("/auth/update")
    public Result update(@RequestHeader("Authorization") String token,
                         @RequestBody UserParams userParams) {
        userService.updateUser(userParams);
        userService.updateToken(token);
        return Result.ok();
    }

    @PostMapping("/auth/head")
    public Result updateHead(@RequestHeader("Authorization") String token, MultipartFile file) {
        String path = userService.updateHead(file);
        if (StringUtils.isEmpty(path)) {
            throw new MyException(ErrorCode.UPLOAD_ERROR);
        }
        userService.updateToken(token);
        return Result.ok();
    }
}
