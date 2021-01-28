package com.leenow.demo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leenow.demo.model.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 16:44
 * @description
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * find User by username
     *
     * @param username username
     * @return Optional<User>
     */
    @Nullable
    User findByUsername(@NonNull @Param("username") String username);


}