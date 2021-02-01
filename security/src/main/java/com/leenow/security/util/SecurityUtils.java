package com.leenow.security.util;

import com.leenow.dao.entity.User;
import org.springframework.util.Assert;

/**
 * @author bg395819 WangLi
 * @date 21/1/26 17:07
 * @description
 */
public class SecurityUtils {
    /**
     * Access token cache prefix.
     */
    private final static String TOKEN_ACCESS_CACHE_PREFIX = "demo.admin.access.token.";

    /**
     * Refresh token cache prefix.
     */
    private final static String TOKEN_REFRESH_CACHE_PREFIX = "demo.admin.refresh.token.";

    private final static String ACCESS_TOKEN_WITH_USER_CACHE_PREFIX = "demo.admin.access_token_with_user.";

    private final static String REFRESH_TOKEN_WITH_USER_CACHE_PREFIX = "demo.admin.refresh_token_with_user.";

    private SecurityUtils() {
    }

    public static String buildTokenAccessKeyWithUser(User user) {
        Assert.notNull(user, "User must not be null");

        return ACCESS_TOKEN_WITH_USER_CACHE_PREFIX + user.getId();
    }

    public static String buildRefreshTokenAccessKeyWithUser(User user) {
        Assert.notNull(user, "User must not be null");

        return REFRESH_TOKEN_WITH_USER_CACHE_PREFIX + user.getId();
    }

    public static String buildAccessTokenKey(String accessToken) {
        Assert.hasText(accessToken, "accessToken must not be blank");

        return TOKEN_ACCESS_CACHE_PREFIX + accessToken;
    }

    public static String buildRefreshTokenKey(String refreshToken) {
        Assert.hasText(refreshToken, "refreshToken must not be blank");

        return TOKEN_REFRESH_CACHE_PREFIX + refreshToken;
    }
}
