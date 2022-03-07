package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.mapper.SysUserMapper;
import com.hu.myblog.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
