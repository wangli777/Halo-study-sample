package com.leenow.security.authentication;

import com.leenow.security.support.UserDetail;
import org.springframework.lang.NonNull;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 18:54
 * @description
 */
public interface Authentication {

    @NonNull
    UserDetail getUserDetail();


}
