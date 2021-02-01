package com.leenow.common.support.constant;

import org.springframework.http.HttpHeaders;

/**
 * @author bg395819 WangLi
 * @date 21/1/26 15:53
 * @description 通用常量
 */
public class CommonConstants {
    public static final String AUTH_TOKEN = HttpHeaders.AUTHORIZATION;

    /**
     * Admin token header name.
     */
    public final static String ADMIN_TOKEN_HEADER_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;
    /**
     * Admin token param name.
     */
    public final static String ADMIN_TOKEN_QUERY_NAME = "admin_token";
}
