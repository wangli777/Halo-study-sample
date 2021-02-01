package com.leenow.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.leenow.dao.model.enums.LogType;
import com.leenow.dao.model.param.LogParam;
import lombok.*;

import java.util.Date;

/**
 * @author bg395819 WangLi
 * @date 21/1/29 15:00
 * @description
 */
@TableName("log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Log extends BaseEntity {

    /**
     * Log key.
     */
    @TableField(value = "log_key")
    private String logKey;

    /**
     * Log type.
     */
    @TableField(value = "type")
    private LogType type;

    /**
     * Log content.
     */
    @TableField(value = "content")
    private String content;

    /**
     * Operator's ip address.
     */
    @TableField(value = "ip_address")
    private String ipAddress;


    public Log(LogParam logParam) {
        super(new Date(), new Date());
        this.logKey = logParam.getLogKey();
        this.type = logParam.getType();
        this.content = logParam.getContent();
        this.ipAddress = logParam.getIpAddress();
//        this(logParam.getLogKey(), logParam.getType(), logParam.getContent(), logParam.getIpAddress());
    }
}
