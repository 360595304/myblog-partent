package com.hu.myblog.utils;

import com.hu.myblog.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Component
public class UserThreadLocal {

    private static final ThreadLocal<SysUser> userThreadLocal = new ThreadLocal<>();

    private UserThreadLocal() {}

    public static void put(SysUser user) {
        userThreadLocal.set(user);
    }

    public static SysUser get() {
        return userThreadLocal.get();
    }

    public static void remove() {
        userThreadLocal.remove();
    }

}
