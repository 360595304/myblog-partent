package com.hu.myblog.service;

import com.hu.myblog.result.Result;
import com.hu.myblog.vo.UserLoginVo;

public interface LoginService {
    Result login(UserLoginVo userLoginVo);

    Result logout(String token);

    Result register(UserLoginVo user);
}
