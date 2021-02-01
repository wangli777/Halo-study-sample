package com.leenow.common.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.leenow.common.exception.BadRequestExpection;
import com.leenow.common.util.JsonUtils;
import com.leenow.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author bg395819 WangLi
 * @date 21/1/28 14:24
 * @description 记录接口请求、响应日志
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAop {

    @Pointcut("execution(* *..*.*.controller..*.*(..))")
    private void controller() {
    }

    @Around("controller()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //被织入advice的类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //方法参数
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = ServletUtils.getCurrentRequest().orElseThrow(() -> new BadRequestExpection("无法获取当前httpServletRequest"));

        //记录请求日志
        printRequestLog(request, className, methodName, args);


        TimeInterval timer = DateUtil.timer();
        Object returnObj = joinPoint.proceed();
        long cost = timer.interval();
        //记录响应日志
        printResponseLog(className, methodName, args, returnObj, cost);

        return returnObj;
    }

    private void printResponseLog(String className, String methodName, Object[] args, Object returnObj, long cost) throws JsonProcessingException {
        if (log.isDebugEnabled()) {
            String returnData = "";
            if (returnObj != null) {
                if (returnObj instanceof ResponseEntity) {
                    ResponseEntity responseEntity = (ResponseEntity) returnObj;
                    if (responseEntity.getBody() instanceof Resource) {
                        returnData = "[ BINARY DATA ]";
                    } else {
                        returnData = toString(Objects.requireNonNull(responseEntity.getBody()));
                    }
                } else {
                    returnData = toString(returnObj);
                }
            }
            log.debug("{}.{} Response: [{}], cost: [{}]ms", className, methodName, returnData, cost);
        }
    }

    @NonNull
    private String toString(@NonNull Object object) throws JsonProcessingException {
        Assert.notNull(object, "Return must not be null");

        String toString = "";
        if (object.getClass().isAssignableFrom(byte[].class) && object instanceof Resource) {
            toString = "[ BINARY DATA ]";
        } else {
            toString = JsonUtils.objectToJson(object);
        }

        return toString;
    }

    private void printRequestLog(HttpServletRequest request, String className, String methodName, Object[] args) throws JsonProcessingException {
        log.debug("Request URL:[{}],URI:[{}],Request Method:[{}],ip:[{}]",
                request.getRequestURL(), request.getRequestURI(),
                methodName, ServletUtil.getClientIP(request));
        //参数为空或者日志级别没有启用debug则不记录请求参数
        if (args == null || !log.isDebugEnabled()) {
            return;
        }

        boolean needLog = true;
        for (Object arg : args) {
            if (arg == null ||
                    arg instanceof HttpServletRequest ||
                    arg instanceof HttpServletResponse ||
                    arg instanceof MultipartFile ||
                    arg.getClass().isAssignableFrom(MultipartFile[].class)) {
                needLog = false;
                break;
            }
        }
        if (needLog) {
            String requestBody = JsonUtils.objectToJson(args);
            log.debug("{}.{} paramters : [{}]", className, methodName, requestBody);
        }
    }
}
