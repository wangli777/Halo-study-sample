package com.leenow.demo.controller;

import com.leenow.demo.common.response.BaseResponse;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.security.token.AuthToken;
import com.leenow.demo.service.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 16:21
 * @description:
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
    public BaseResponse<AuthToken> login(@RequestBody @Valid LoginParam loginParam){
        AuthToken token = userService.loginCheck(loginParam);

        return BaseResponse.ok(token);
    }
}
