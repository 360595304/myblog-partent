package com.hu.myblog.handler;

import com.hu.myblog.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author suhu
 * @createDate 2022/3/8
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.fail(-999, "系统异常");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result myExceptionHandler(MyException e) {
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMsg());
    }
}
