package com.hu.myblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.handler.MyException;
import com.hu.myblog.mapper.SysUserMapper;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.service.OSSService;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.JWTUtils;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.params.UserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private OSSService ossService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findUser(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "account", "avatar", "nickname", "mobile_phone_number", "email",
                        "create_date", "last_login")
                .eq("account", username)
                .eq("password", password);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser getUserByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) return null;
        String userJSON = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(userJSON)) return null;
        return JSONObject.parseObject(userJSON, SysUser.class);
    }

    @Override
    public SysUser findUserByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username).last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateUser(UserParams userParams) {
        Long userId = UserThreadLocal.get().getId();
        if (!Objects.equals(userId, userParams.getId()) && userId != 1L) {
            throw new MyException(ErrorCode.NO_PERMISSION);
        }
        SysUser user = userMapper.selectById(userParams.getId());
        user.setNickname(userParams.getNickname());
        userMapper.updateById(user);
    }

    @Override
    public String updateHead(MultipartFile file) {
        Long userId = UserThreadLocal.get().getId();
        SysUser user = userMapper.selectById(userId);
        String avatarPath = ossService.uploadFile(file, "head");
        user.setAvatar(avatarPath);
        userMapper.updateById(user);
        return avatarPath;
    }

    @Override
    public void updateToken(String token) {
        Long userId = UserThreadLocal.get().getId();
        SysUser user = userMapper.selectById(userId);
        String userInfo = JSONObject.toJSONString(user);
        redisTemplate.opsForValue().set("TOKEN_" + token, userInfo);
    }
}
