package user;

import com.leenow.dao.entity.User;
import com.leenow.service.user.UserService;
import com.leenow.web.WebApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author WangLi
 * @date 2021/2/1 22:50
 * @description
 */
@SpringBootTest(classes = WebApplication.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void authenticate() {
        Optional<User> adminUser = userService.getAdminUser();
        Assertions.assertTrue(adminUser.isPresent());
    }
}