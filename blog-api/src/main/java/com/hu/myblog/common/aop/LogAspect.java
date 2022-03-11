package com.hu.myblog.common.aop;

import com.alibaba.fastjson.JSON;
import com.hu.myblog.utils.HttpContextUtils;
import com.hu.myblog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author suhu
 * @createDate 2022/3/10
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    // 切入点
    @Pointcut("@annotation(com.hu.myblog.common.aop.LogAnnotation)")
    public void pt(){}

    // 环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long time = System.currentTimeMillis() - beginTime;

        // 保存日志
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start===================");
        log.info("module:{}", logAnnotation.module());
        log.info("operation:{}", logAnnotation.operator());

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className+"."+methodName+"()");
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));
        log.info("excute time : {} ms", time);
        log.info("=====================log end====================");
    }

}
