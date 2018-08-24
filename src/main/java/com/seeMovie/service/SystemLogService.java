package com.seeMovie.service;

import com.seeMovie.pojo.SystemLogVo;

/**
 * @author 		mym
 * @date 		2018-08-24 19:02:02
 * @describe 	系统内部日志接口
 * @version 	V1.0
 * @param
 * @return
 */
public interface SystemLogService {
	/**
	 * 
	 * @author		mym
	 * @date        2018年8月24日下午7:05:28
	 * @describe    新增系统日志
	 * @return      void
	 */
	void insertSystemLog(SystemLogVo systemLogVo);
	
}
