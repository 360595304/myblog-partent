package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.vo.params.UserParams;
import org.springframework.web.multipart.MultipartFile;

public interface SysUserService extends IService<SysUser> {
    SysUser findUser(String username, String password);

    SysUser getUserByToken(String token);

    SysUser findUserByUsername(String username);

    void updateUser(UserParams userParams);

    String updateHead(MultipartFile file);

    void updateToken(String token);
}
