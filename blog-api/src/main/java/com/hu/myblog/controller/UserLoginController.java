package com.hu.myblog.controller;

import com.hu.myblog.result.Result;
import com.hu.myblog.service.LoginService;
import com.hu.myblog.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author suhu
 * @createDate 2022/3/9
 */
@RestController
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginVo userLoginVo) {
        return loginService.login(userLoginVo);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserLoginVo user) {
        return loginService.register(user);
    }
}
