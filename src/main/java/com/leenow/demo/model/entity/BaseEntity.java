package com.leenow.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 15:49
 * @description:
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseEntity {
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}
