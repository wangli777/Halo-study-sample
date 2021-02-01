package com.leenow.security.authentication;

import com.leenow.security.support.UserDetail;
import org.springframework.lang.NonNull;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 18:55
 * @description
 */
public class AuthenticationImpl implements Authentication {

    private final UserDetail userDetail;

    public AuthenticationImpl(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    @NonNull
    public UserDetail getUserDetail() {
        return userDetail;
    }
}
