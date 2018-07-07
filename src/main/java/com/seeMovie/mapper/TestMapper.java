package com.seeMovie.mapper;

import java.util.List;

import com.seeMovie.pojo.MovieVo;
public interface TestMapper {
	//保存当前电影资源
	void insertaHrefByVo(MovieVo vo);
	//根据保存的截取链接判断当前数据是否存在  存在则不保存
	int selectaHrefVoByHref(String getaHref);
	//查找所有电影信息
	List<MovieVo> selectAllMovieVo();

}
