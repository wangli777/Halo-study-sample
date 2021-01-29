package com.leenow.demo.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 17:18
 * @description
 */
@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 100, message = "用户名长度不能超过{max}")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(max = 100, message = "密码长度不能超过{max}")
    private String password;
}
