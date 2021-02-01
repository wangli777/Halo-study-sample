package com.leenow.security.support;

import com.leenow.common.exception.AuthenticationException;
import com.leenow.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 18:58
 * @description
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
