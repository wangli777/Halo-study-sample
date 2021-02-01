package com.leenow.service.listener;

import com.leenow.dao.entity.Log;
import com.leenow.service.event.LogEvent;
import com.leenow.service.log.LogService;
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

    private final LogService logService;

    public LogEventListener(LogService logService) {
        this.logService = logService;
    }

    @EventListener
    @Async
    public void onLogEvent(LogEvent logEvent) {

        // 记录操作日志到日志表
        logService.save(new Log(logEvent.getLogParam()));

    }
}
