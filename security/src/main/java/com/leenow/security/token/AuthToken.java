package com.leenow.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 16:49
 * @description
 */
@Data
public class AuthToken {
    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * Expired in. (seconds)
     */
    @JsonProperty("expired_in")
    private int expiredIn;
}
