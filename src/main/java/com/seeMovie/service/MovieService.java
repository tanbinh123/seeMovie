package com.seeMovie.service;

import java.util.List;
import java.util.Map;

import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
public interface MovieService {
	//新增电影资源
	void insertAlldownHrefByList(List<Map<String, Object>> downHrefList, String webLinks);
    //查找所有电影
	List<List<MovieVo>> selectAllMovieVo(PagingUtil pagingUtil);
	//查找所有电影总数
	int selectMovieVoCount();
	//根据电影id查看电影详情
	MovieVo getMovieDetail(String movieId);

}
