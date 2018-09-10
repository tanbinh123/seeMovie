package com.seeMovie.service;

import java.util.List;
import java.util.Map;

import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.pojo.WebLinksVo;
public interface MovieService {
	//新增电影资源
	int insertAlldownHrefByList(List<Map<String, Object>> downHrefList, WebLinksVo webLinksVo);
	//根据电影id查看电影详情
	MovieVo getMovieDetail(String movieId);
	//定时更新影片信息  例如影片类型、影片分类等
	void UpdateMovieCategoryInfoTimer();
	//根据参数查找影片信息
	Map<String, Object> selectMovieInfoByParam(PagingUtil pagingUtil, Map<String, Object> map);
	//定时更新影片的图片链接信息 无法访问的图片链接用默认图片代替
	void UpdateMovieImgUrlInfoTimer(MovieVo movieVo);
	//每次查询两条网站初始化链接
	List<WebLinksVo> getWebLinksVo();
	//将当前已经爬取过的网站爬取状态改为Y
	void updateWebLinks(WebLinksVo webLinksVo);
	//查询所有没有更应图片链接的影片信息
    List<MovieVo> selectAllNoUpdateImgUrlVo();
	//多条件查询影片信息
    Map<String, Object> selectAllMovieByParam(Map<String, Object> returnMap);
	//新增影片信息
	void insertMovieVo(MovieVo movieVo);
	//编辑影片信息
	void updateMovieVo(MovieVo movieVo);
	//删除影片信息
	int deleteMovieVo(String[] strings);
}
