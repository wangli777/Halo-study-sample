package com.leenow.demo.controller;

import com.leenow.demo.common.response.BaseResponse;
import com.leenow.demo.exception.NotFoundExpection;
import com.leenow.demo.model.entity.User;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.security.token.AuthToken;
import com.leenow.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author LeeNow WangLi
 * @date 2021/1/24 16:21
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public BaseResponse<AuthToken> login(@RequestBody @Valid LoginParam loginParam) {
        AuthToken token = userService.loginCheck(loginParam);

        return BaseResponse.ok(token);
    }

    @PostMapping("logout")
    public void logout() {
        userService.clearToken();
    }

    @GetMapping("info")
    public BaseResponse<User> info() {
        return BaseResponse.ok(
                userService.getAdminUser().orElseThrow(
                        () -> new NotFoundExpection("找不到用户信息")));
    }

    @PostMapping("put")
    public BaseResponse<AuthToken> putEntry(@RequestBody @Valid LoginParam loginParam) {
//        AuthToken token = userService.loginCheck(loginParam);
        userService.put(loginParam);

        return BaseResponse.ok("ok");
    }
}
