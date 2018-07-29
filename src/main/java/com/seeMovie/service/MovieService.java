package com.seeMovie.service;

import java.util.List;
import java.util.Map;

import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.pojo.WebLinksVo;
public interface MovieService {
	//新增电影资源
	void insertAlldownHrefByList(List<Map<String, Object>> downHrefList, WebLinksVo webLinksVo);
	//根据电影id查看电影详情
	MovieVo getMovieDetail(String movieId);
	//定时更新影片信息  例如影片类型、影片分类等
	void UpdateMovieCategoryInfoTimer();
	//根据参数查找影片信息
	Map<String, Object> selectMovieInfoByParam(PagingUtil pagingUtil, Map<String, Object> map);
	//定时更新影片的图片链接信息 无法访问的图片链接用默认图片代替
	void UpdateMovieImgUrlInfoTimer();
	//每次查询两条网站初始化链接
	List<WebLinksVo> getWebLinksVo();
	//将当前已经爬取过的网站爬取状态改为Y
	void updateWebLinks(WebLinksVo webLinksVo);
}
