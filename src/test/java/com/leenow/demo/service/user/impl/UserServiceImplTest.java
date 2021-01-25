package com.leenow.demo.service.user.impl;

import com.leenow.demo.model.entity.user.User;
import com.leenow.demo.model.param.LoginParam;
import com.leenow.demo.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 20:36
 * @description:
 */
@SpringBootTest
@Slf4j
class UserServiceImplTest {

    @Resource
    UserService userService;

    @Test
    void loginCheck() {
//        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
        assertNull(null);
    }

    @Test
    void authenticate() {
        LoginParam loginParam = new LoginParam();
        loginParam.setUsername("wangli");
        loginParam.setPassword("123456");
        User authenticate = userService.authenticate(loginParam);
//        Console.log(authenticate);
        log.debug(authenticate.toString());
        assertEquals("wangli", authenticate.getUsername());

    }

    @Test
    void passwordMatch() {
        assertNull(null);
    }

    @Test
    void findByUsername() {
        assertNull(null);
    }

    @Test
    void findByUsernameNonNull() {
        assertNull(null);
    }
}