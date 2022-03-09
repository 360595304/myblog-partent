package com.hu.myblog;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
public class TestDemo {
    private static final String slat = "@#$qqz666";
    public static void main(String[] args) {
        String password = DigestUtils.md5Hex("123456" + slat);
        System.out.println(password);
    }
}
