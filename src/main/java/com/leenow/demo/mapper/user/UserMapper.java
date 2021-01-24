package com.leenow.demo.mapper.user;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leenow.demo.model.entity.user.User;
import org.springframework.lang.NonNull;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 16:44
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {
    @NonNull
    Optional<User> findByUsername(@NonNull @Param("username")String username);


}