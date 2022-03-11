package com.hu.myblog.result;

public enum ErrorCode {
    PARAMS_ERROR(10001, "参数有误"),
    USERNAME_PASSWORD_ERROR(10002, "用户名或密码错误"),
    TOKEN_ERROR(10003, "TOKEN不合法"),
    USER_EXIST(10004, "用户已存在"),
    UPLOAD_ERROR(20001, "文件上传失败"),
    NO_PERMISSION(70001, "没有权限"),
    ARTICLE_NOT_FOUND(70002, "没有找到该文章"),
    SESSION_TIMEOUT(90001, "会话超时"),
    NO_LOGIN(90002, "未登录"),
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
