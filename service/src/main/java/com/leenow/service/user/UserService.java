package com.leenow.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leenow.dao.model.param.LoginParam;
import com.leenow.security.token.AuthToken;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.leenow.dao.entity.User;

import java.util.Optional;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 16:44
 * @description
 */
public interface UserService extends IService<User> {
    /**
     * Expired seconds.
     */
    int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    int REFRESH_TOKEN_EXPIRED_DAYS = 30;

    /**
     * 用户登录
     *
     * @param loginParam 用户输入的登录信息
     * @return 登录成功返回token
     */
    @NonNull
    AuthToken loginCheck(@NonNull LoginParam loginParam);

    /**
     * 根据输入的登录信息查找User
     *
     * @param loginParam 用户输入的登录信息
     * @return 匹配的User
     */
    @NonNull
    User authenticate(@NonNull LoginParam loginParam);

    /**
     * 根据用户名查找用户信息
     *
     * @param username 输入的用户名
     * @return Optional<User>
     */
    Optional<User> findByUsername(@NonNull String username);

    /**
     * 根据用户名查找用户信息，返回的User一定不为null，否则抛出异常
     *
     * @param username 输入的用户名
     * @return User
     */
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

    /**
     * 获取管理员用户
     *
     * @return Optional<User>
     */
    Optional<User> getAdminUser();

    /**
     * 清除Token
     */
    void clearToken();

    /**
     * 存数据-测试用
     *
     * @param loginParam 输入的数据
     */
    void put(LoginParam loginParam);
}
