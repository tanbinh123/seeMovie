package com.seeMovie.service;

import com.seeMovie.pojo.SystemLogVo;

import java.util.Map;

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
	/**
	 * @author      mym
	 * @date        2018/9/8 0008 13:42
	 * @describe    [returnMap] 多条件查询系统日志
	 * @version     V1.0
	 * @param       [returnMap]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
    Map<String, Object> selectAllSystemLogInfoByParam(Map<String, Object> returnMap);
}
