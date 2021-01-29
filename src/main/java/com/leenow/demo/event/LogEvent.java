package com.leenow.demo.event;

import com.leenow.demo.model.enums.LogType;
import com.leenow.demo.model.param.LogParam;
import com.leenow.demo.util.ServletUtils;
import com.leenow.demo.util.ValidationUtils;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author bg395819 WangLi
 * @date 21/1/28 19:03
 * @description 操作日志事件
 */
public class LogEvent extends ApplicationEvent {

    @Getter
    private final transient LogParam logParam;


    public LogEvent(Object source, LogParam logParam) {
        super(source);

        // 手动验证参数
        ValidationUtils.validate(logParam);

        // 设置ip地址
        logParam.setIpAddress(ServletUtils.getRequestIp());
        this.logParam = logParam;
    }

    public LogEvent(Object source, String logKey, LogType type, String content) {
        this(source, new LogParam(logKey, type, content));
    }
}
