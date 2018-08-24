package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.SystemLogVoMapper;
import com.seeMovie.pojo.SystemLogVo;
import com.seeMovie.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemLogServiceImpl implements SystemLogService {
	@Autowired
	SystemLogVoMapper systemLogVoMapper;
	/**
	 * 
	 * @author		mym
	 * @date        2018年8月24日下午7:05:28
	 * @describe    新增系统日志
	 * @return      void
	 */
	@Override
	public void insertSystemLog(SystemLogVo systemLogVo) {
		systemLogVoMapper.insert(systemLogVo);
	}
}
