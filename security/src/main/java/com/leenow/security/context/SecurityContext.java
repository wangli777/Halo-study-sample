package com.leenow.security.context;


import com.leenow.security.authentication.Authentication;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 19:33
 * @description
 */
public interface SecurityContext {
    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

    /**
     * Check if the current context has authenticated or not.
     *
     * @return true if authenticate; false otherwise
     */
    default boolean isAuthenticated() {
        return getAuthentication() != null;
    }

}
