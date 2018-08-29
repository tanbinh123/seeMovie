package com.seeMovie.service;

import java.util.Map;

/**
 * @author      mym
 * @date        2018/8/29 0029 9:14
 * @describe	查看访问者信息的接口
 * @version     V1.0
 * @param       
 * @return      
*/
public interface VisitService {

	//根据条件查询对应访问者的ip信息
	Map<String, Object> selectAllVisitInfoByParam(Map<String, Object> returnMap);
}
