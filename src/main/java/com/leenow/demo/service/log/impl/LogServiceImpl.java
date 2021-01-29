package com.leenow.demo.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leenow.demo.mapper.LogMapper;
import com.leenow.demo.model.entity.Log;
import com.leenow.demo.service.log.LogService;
import org.springframework.stereotype.Service;

/**
 * @author bg395819 WangLi
 * @date 21/1/29 15:17
 * @description
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
