package com.leenow.service.log.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leenow.dao.entity.Log;
import com.leenow.dao.mapper.LogMapper;
import com.leenow.service.log.LogService;
import org.springframework.stereotype.Service;

/**
 * @author bg395819 WangLi
 * @date 21/1/29 15:17
 * @description
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
