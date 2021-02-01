package com.leenow.dao.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leenow.dao.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

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

    Optional<User> findByUsername(@NonNull @Param("username") String username);


}