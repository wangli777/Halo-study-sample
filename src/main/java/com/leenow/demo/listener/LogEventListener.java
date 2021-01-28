package com.leenow.demo.listener;

import com.leenow.demo.event.LogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author bg395819 WangLi
 * @date 21/1/28 19:05
 * @description 操作日志事件监听器
 */
@Component
public class LogEventListener {
    @EventListener
    @Async
    public void onLogEvent(LogEvent logEvent) {
        // TODO: 21/1/28 记录操作日志到日志表

    }
}
