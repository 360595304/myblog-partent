package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    SysUser findUser(String username, String password);

    SysUser getUserByToken(String token);

    SysUser findUserByUsername(String username);
}
