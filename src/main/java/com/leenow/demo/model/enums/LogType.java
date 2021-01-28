package com.leenow.demo.model.enums;

/**
 * Log type.
 */
public enum LogType implements ValueEnum<Integer> {

    /**
     * Logged in
     */
    LOGGED_IN(25),

    /**
     * Logged out
     */
    LOGGED_OUT(30),

    /**
     * Logged failed.
     */
    LOGIN_FAILED(35);

    private final Integer value;

    LogType(Integer value) {
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return value;
    }
}
