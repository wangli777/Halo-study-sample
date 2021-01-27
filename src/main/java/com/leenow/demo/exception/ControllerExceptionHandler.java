package com.leenow.demo.exception;

import com.leenow.demo.common.response.BaseResponse;
import com.leenow.demo.util.ExceptionUtils;
import com.leenow.demo.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @author: bg395819 WangLi
 * @date: 21/1/26 18:26
 * @description:
 */
@RestControllerAdvice(value = {"com.leenow.demo.controller"})
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BaseResponse<Map<String, String>> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage("字段验证错误，请完善后重试！");
        Map<String, String> errMap = ValidationUtils.mapWithFieldError(e.getBindingResult().getFieldErrors());
        baseResponse.setData(errMap);
        return baseResponse;
    }

    @ExceptionHandler(AbstractBaseException.class)
    public ResponseEntity<BaseResponse<?>> handleCustomizeException(AbstractBaseException e) {

        BaseResponse<Object> baseResponse = handleBaseException(e);
        baseResponse.setStatus(e.getStatus().value());
        baseResponse.setData(e.getErrorData());
        return new ResponseEntity<>(baseResponse, e.getStatus());

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Exception e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setStatus(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        BaseResponse<T> baseResponse = new BaseResponse<>();

        baseResponse.setMessage(t.getMessage());

        if (log.isDebugEnabled()) {
            log.error("Captured an exception:", t);
            baseResponse.setDevMessage(ExceptionUtils.getStackTrace(t));
        } else {
            log.error("Captured an exception: [{}]", t.getMessage());
        }

        return baseResponse;
    }
}
