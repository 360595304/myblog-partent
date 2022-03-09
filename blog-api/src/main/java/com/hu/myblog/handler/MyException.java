package com.hu.myblog.handler;

import com.hu.myblog.result.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MyException extends RuntimeException {
    private int code;

    private String msg;

    public MyException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
}
