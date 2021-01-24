package com.leenow.demo.security.support;

import com.leenow.demo.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import com.leenow.demo.exception.AuthenticationException;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 18:58
 * @description:
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private User user;

    /**
     * Gets user info.
     *
     * @return user info
     * @throws AuthenticationException throws if the user is null
     */
    @NonNull
    public User getUser() {
        return user;
    }

    /**
     * Sets user info.
     *
     * @param user user info
     */
    public void setUser(User user) {
        this.user = user;
    }
}
