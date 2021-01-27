package com.leenow.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 18:14
 * @description:
 */
public class NotFoundExpection extends AbstractBaseException {

    public NotFoundExpection(String message) {
        super(message);
    }

    public NotFoundExpection(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
