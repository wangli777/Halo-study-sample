package com.leenow.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 18:14
 * @description
 */
public class BadRequestExpection extends AbstractBaseException {

    public BadRequestExpection(String message) {
        super(message);
    }

    public BadRequestExpection(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
