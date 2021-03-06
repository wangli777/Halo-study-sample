package com.leenow.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leenow.common.cache.AbstractStringCacheStore;
import com.leenow.common.config.properties.AppConfigProperties;
import com.leenow.common.exception.AuthenticationException;
import com.leenow.dao.entity.User;
import com.leenow.security.authentication.AuthenticationImpl;
import com.leenow.security.context.SecurityContextHolder;
import com.leenow.security.context.SecurityContextImpl;
import com.leenow.security.support.UserDetail;
import com.leenow.security.util.SecurityUtils;
import com.leenow.service.user.UserService;
import com.leenow.web.handler.DefaultAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.leenow.common.support.constant.CommonConstants.ADMIN_TOKEN_HEADER_NAME;
import static com.leenow.common.support.constant.CommonConstants.ADMIN_TOKEN_QUERY_NAME;


/**
 * Admin authentication filter.
 *
 * @author johnniang
 */
@Slf4j
@Component
@Order(1)
public class AdminAuthenticationFilter extends AbstractAuthenticationFilter {


    private final UserService userService;

    public AdminAuthenticationFilter(AbstractStringCacheStore cacheStore,
                                     UserService userService,
                                     AppConfigProperties configProperties,
                                     ObjectMapper objectMapper) {
        super(configProperties, cacheStore);
        this.userService = userService;

        //需要过滤的url
        addUrlPatterns("/api/user/**");

        addExcludeUrlPatterns(
                "/api/user/login",
                "/api/user/put",
//                "/api/user/logout",
                "/api/user/refresh/*",
                "/api/user/password/code",
                "/api/user/password/reset"
        );

        // set failure handler
        DefaultAuthenticationFailureHandler failureHandler = new DefaultAuthenticationFailureHandler();
//        failureHandler.setProductionEnv(configProperties.isProductionEnv());
        failureHandler.setObjectMapper(objectMapper);

        setFailureHandler(failureHandler);

    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!configProperties.isAuthEnabled()) {
            // Set security
            userService.getAdminUser().ifPresent(user ->
                    SecurityContextHolder.setContext(new SecurityContextImpl(new AuthenticationImpl(new UserDetail(user)))));

            // Do filter
            filterChain.doFilter(request, response);
            return;
        }

        // Get token from request
        String token = getTokenFromRequest(request);

        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("未登录，请登录后访问");
        }

        // Get user id from cache
        Optional<Integer> optionalUserId = cacheStore.getAny(SecurityUtils.buildAccessTokenKey(token), Integer.class);

        if (!optionalUserId.isPresent()) {
            throw new AuthenticationException("Token 已过期或不存在").setErrorData(token);
        }

        // Get the user
        User user = userService.getById(optionalUserId.get());

        // Build user detail
        UserDetail userDetail = new UserDetail(user);

        // Set security
        SecurityContextHolder.setContext(new SecurityContextImpl(new AuthenticationImpl(userDetail)));

        // Do filter
        filterChain.doFilter(request, response);
    }

    @Override
    protected String getTokenFromRequest(@NonNull HttpServletRequest request) {
        return getTokenFromRequest(request, ADMIN_TOKEN_QUERY_NAME, ADMIN_TOKEN_HEADER_NAME);
    }

}
