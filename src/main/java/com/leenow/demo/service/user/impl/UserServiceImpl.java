package com.leenow.demo.service.user.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leenow.demo.cache.AbstractStringCacheStore;
import com.leenow.demo.event.LogEvent;
import com.leenow.demo.exception.BadRequestExpection;
import com.leenow.demo.exception.NotFoundExpection;
import com.leenow.demo.mapper.user.UserMapper;
import com.leenow.demo.model.entity.User;
import com.leenow.demo.model.enums.LogType;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.security.authentication.Authentication;
import com.leenow.demo.security.context.SecurityContextHolder;
import com.leenow.demo.security.token.AuthToken;
import com.leenow.demo.security.util.SecurityUtils;
import com.leenow.demo.service.user.UserService;
import com.leenow.demo.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 16:44
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AbstractStringCacheStore cacheStore;

    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(AbstractStringCacheStore cacheStore, ApplicationEventPublisher eventPublisher) {
        this.cacheStore = cacheStore;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @NonNull
    public AuthToken loginCheck(@NonNull final LoginParam loginParam) {

        //get user
        final User user = this.authenticate(loginParam);

        if (SecurityContextHolder.getContext().isAuthenticated()) {
            throw new BadRequestExpection("您已登录，请不要重复登录");
        }

        // Log it then login successful
        eventPublisher.publishEvent(new LogEvent(this, user.getUsername(), LogType.LOGGED_IN, user.getNickname()));

        //Generate accessToken
        return buildAuthToken(user);
    }

    @NonNull
    private AuthToken buildAuthToken(@NonNull User user) {
        Assert.notNull(user, "User must not be null");

        AuthToken authToken = new AuthToken();

        authToken.setAccessToken(UUIDUtils.randomUUIDWithoutDash());
        authToken.setRefreshToken(UUIDUtils.randomUUIDWithoutDash());
        authToken.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);

        // Cache those tokens with userId as key
        cacheStore.putAny(SecurityUtils.buildTokenAccessKeyWithUser(user), authToken.getAccessToken(), ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        cacheStore.putAny(SecurityUtils.buildRefreshTokenAccessKeyWithUser(user), authToken.getRefreshToken(), REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);

        // Cache those tokens with userId as value
        cacheStore.putAny(SecurityUtils.buildAccessTokenKey(authToken.getAccessToken()), user.getId(), ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        cacheStore.putAny(SecurityUtils.buildRefreshTokenKey(authToken.getRefreshToken()), user.getId(), REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);

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
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGIN_FAILED, loginParam.getUsername()));
            throw new BadRequestExpection(msg);
        }

        if (!passwordMatch(user, loginParam.getPassword())) {
            //记录登录失败事件
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGIN_FAILED, loginParam.getUsername()));

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
    public Optional<User> getAdminUser() {
        return getBaseMapper().findByUsername("wangli");
    }

    @Override
    public void clearToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestExpection("您尚未登录，无法注销");
        }
        User user = authentication.getUserDetail().getUser();

        cacheStore.getAny(SecurityUtils.buildTokenAccessKeyWithUser(user), String.class).ifPresent(accessToken -> {
            //remove accessToken
            cacheStore.delete(SecurityUtils.buildAccessTokenKey(accessToken));
            cacheStore.delete(SecurityUtils.buildTokenAccessKeyWithUser(user));
        });

        cacheStore.getAny(SecurityUtils.buildRefreshTokenAccessKeyWithUser(user), String.class).ifPresent(refreshToken -> {
            //remove accessToken
            cacheStore.delete(SecurityUtils.buildRefreshTokenKey(refreshToken));
            cacheStore.delete(SecurityUtils.buildRefreshTokenAccessKeyWithUser(user));
        });
        // logout event log
        eventPublisher.publishEvent(new LogEvent(this, user.getUsername(), LogType.LOGGED_OUT, user.getUsername()));

        log.info("You have been logged out!");
    }

    @Override
    public void put(LoginParam loginParam) {
        cacheStore.put(loginParam.getUsername(), loginParam.getPassword());
    }

    @Override
    public Optional<User> findByUsername(String username) {
//        return Optional.ofNullable(this.getBaseMapper().findByUsername(username));
        return this.getBaseMapper().findByUsername(username);
    }

    @Override
    public User findByUsernameNonNull(String username) {
        return findByUsername(username).orElseThrow(() -> new NotFoundExpection("The username does not exist").setErrorData(username));
    }
}
