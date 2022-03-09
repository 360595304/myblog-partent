package com.hu.myblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.LoginService;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.JWTUtils;
import com.hu.myblog.vo.UserLoginVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String slat = "@#$qqz666";

    @Override
    public Result login(UserLoginVo userLoginVo) {
        String username = userLoginVo.getUsername();
        String password = userLoginVo.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        password = DigestUtils.md5Hex(password + slat);
        SysUser userInfo = userService.findUser(username, password);
        if (userInfo == null) {
            return Result.fail(ErrorCode.USERNAME_PASSWORD_ERROR);
        }
        String token = JWTUtils.createToken(userInfo.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSONObject.toJSONString(userInfo), 7, TimeUnit.DAYS);
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("token", token);
        }};
        return Result.ok(result);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.ok().message("退出成功");
    }

    @Override
    public Result register(UserLoginVo user) {
        // 验证参数
        String username = user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        // 查询用户是否已存在
        SysUser isExist = userService.findUserByUsername(username);
        if (null != isExist) {
            return Result.fail(ErrorCode.USER_EXIST);
        }
        // 设置用户信息并保存至数据库
        SysUser sysUser = new SysUser();
        sysUser.setAccount(username);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setNickname(nickname);
        sysUser.setCreateDate(new Date());
        sysUser.setLastLogin(new Date());
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setStatus("");
        userService.save(sysUser);
        // 生成token并存入redis
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSONObject.toJSONString(sysUser), 7, TimeUnit.DAYS);
        // 返回token
        Map<String, String> result = new HashMap<String, String>(){{
            put("token", token);
        }};
        return Result.ok(result);
    }
}
