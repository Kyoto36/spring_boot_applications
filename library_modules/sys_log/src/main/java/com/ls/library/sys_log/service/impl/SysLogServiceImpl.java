package com.ls.library.sys_log.service.impl;

import com.ls.common.basics.service.SuperServiceImpl;
import com.ls.library.sys_log.mapper.SysOperateLogMapper;
import com.ls.library.sys_log.model.SysOperateLog;
import com.ls.library.sys_log.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends SuperServiceImpl<SysOperateLogMapper, SysOperateLog> implements SysLogService {
}
