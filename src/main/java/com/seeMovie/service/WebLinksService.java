package com.seeMovie.service;

import com.seeMovie.pojo.WebLinksVo;

import java.util.Map;

/**
 * @author      mym
 * @date        2018/9/5 0005 18:36
 * @describe    操作待爬取网站相关接口
 * @version     V1.0
 * @param       操作待爬取网站相关接口
 * @return      
*/
public interface WebLinksService {
	//多条件查询符合条件的待爬取网站信息
	Map<String, Object> selectAllWebLinksByParam(Map<String, Object> returnMap);

	void insertWebLinkVo(WebLinksVo webLinksVo);
}
