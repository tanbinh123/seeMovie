package com.seeMovie.service;

import java.util.Map;

/**
 * @author      mym
 * @date        2018/8/25 0025 15:20
 * @describe    菜单相关操作接口
 * @version     V1.0
 * @param       菜单相关操作接口
 * @return      
*/
public interface MenuService {
	/**
	 * @author      mym
	 * @date        2018/8/25 0025 15:23
	 * @describe    [根据条件查询菜单数据集合]
	 * @version     V1.0
	 * @param       [returnMap]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	Map<String, Object> selectMenuListByParam(Map<String, Object> returnMap);
}
