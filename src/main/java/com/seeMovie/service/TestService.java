package com.seeMovie.service;

import java.util.List;

import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
public interface TestService {
	//新增
	void insertAllaHrefByList(List<String> aHrefList, String httpHeader);
    //查找所有电影
	List<List<MovieVo>> selectAllMovieVo(PagingUtil pagingUtil);
	//查找所有电影总数
	int selectMovieVoCount();

}
