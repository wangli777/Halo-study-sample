package com.leenow.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author bg395819 WangLi
 * @date 21/1/28 19:03
 * @description 操作日志事件
 */
public class LogEvent extends ApplicationEvent {


    public LogEvent(Object source) {
        super(source);
    }
}
