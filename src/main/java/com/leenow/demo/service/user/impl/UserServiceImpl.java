package com.leenow.demo.service.user.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.leenow.demo.exception.BadRequestExpection;
import com.leenow.demo.exception.NotFoundExpection;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.security.context.SecutiryContextHolder;
import com.leenow.demo.security.token.AuthToken;
import com.leenow.demo.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leenow.demo.model.entity.user.User;
import com.leenow.demo.mapper.user.UserMapper;
import com.leenow.demo.service.user.UserService;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 16:44
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @NonNull
    public AuthToken loginCheck(@NonNull final LoginParam loginParam) {

        //get user
        final User user = this.authenticate(loginParam);

        if (SecutiryContextHolder.getContext().isAuthenticated()) {
            throw new BadRequestExpection("您已登录，请不要重复登录");
        }

        // Log it then login successful
        // TODO: 2021/1/24 事件-登录成功

        //Generate accessToken


        return buildAuthToken(user);
    }

    @NonNull
    private AuthToken buildAuthToken(@NonNull User user) {
        Assert.notNull(user,"User must not be null");

        AuthToken authToken = new AuthToken();

        authToken.setAccessToken(UUIDUtils.randomUUIDWithoutDash());
        authToken.setRefreshToken(UUIDUtils.randomUUIDWithoutDash());
        authToken.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);

        // TODO: 2021/1/24 缓存authToken

        return authToken;
    }

    @Override
    public User authenticate(LoginParam loginParam) {
        Assert.notNull(loginParam, "Login Param must not be null");

        String username = loginParam.getUsername();

        final User user;
        String msg = "用户名或密码错误";

        try {
            user = findByUsernameNonNull(username);
        } catch (NotFoundExpection e) {
            log.error("Fail to find username:" + username);
            //记录登录失败事件
            throw new BadRequestExpection(msg);
        }

        if (!passwordMatch(user, loginParam.getPassword())) {
            //记录登录失败事件
            throw new BadRequestExpection(msg);
        }
        return user;
    }

    @Override
    public boolean passwordMatch(@NonNull User user, @Nullable String password) {
        Assert.notNull(user, "User must not be null");
        return CharSequenceUtil.isNotBlank(user.getPassword()) && BCrypt.checkpw(password, user.getPassword());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.getBaseMapper().findByUsername(username);
    }

    @Override
    public User findByUsernameNonNull(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundExpection("The username does not exist").setErrorData(username));
    }
}
