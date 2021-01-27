package com.leenow.demo.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leenow.demo.model.entity.user.User;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.security.token.AuthToken;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 16:44
 * @description:
 */
public interface UserService extends IService<User> {
    /**
     * Expired seconds.
     */
    int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    int REFRESH_TOKEN_EXPIRED_DAYS = 30;

    @NonNull
    AuthToken loginCheck(@NonNull LoginParam loginParam);

    @NonNull
    User authenticate(@NonNull LoginParam loginParam);

    @NonNull
    Optional<User> findByUsername(@NonNull String username);

    @NonNull
    User findByUsernameNonNull(@NonNull String username);

    /**
     * Checks the password is match the user password.
     *
     * @param user     user info must not be null
     * @param password plain password
     * @return true if the given password is match the user password; false otherwise
     */
    boolean passwordMatch(@NonNull User user, @Nullable String password);

    Optional<User> getAdminUser();

    void clearToken();

    void put(LoginParam loginParam);
}
