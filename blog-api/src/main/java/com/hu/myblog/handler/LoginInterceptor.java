package com.hu.myblog.handler;

import com.alibaba.fastjson.JSON;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SysUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser user = userService.getUserByToken(token);
        if (user == null) {
            Result result = Result.fail(ErrorCode.TOKEN_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
