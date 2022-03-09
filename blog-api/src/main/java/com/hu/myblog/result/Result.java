package com.hu.myblog.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Boolean success;
    private int code;
    private String msg;

    private Object data;

    public static Result ok(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result ok() {
        return new Result(true, 200, "success", null);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(false, errorCode.getCode(), errorCode.getMsg(), null);
    }

    public Result message(String msg) {
        this.setMsg(msg);
        return this;
    }

}
