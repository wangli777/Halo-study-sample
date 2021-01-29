package com.leenow.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 18:10
 * @description
 */
public abstract class AbstractBaseException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    protected AbstractBaseException(String message) {
        super(message);
    }

    protected AbstractBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Http status code
     *
     * @return {@link HttpStatus}
     */
    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AbstractBaseException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
