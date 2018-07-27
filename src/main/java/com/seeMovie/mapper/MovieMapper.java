package com.seeMovie.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seeMovie.pojo.MovieVo;
public interface MovieMapper {
	//保存当前电影资源
	void insertDownHrefByVo(MovieVo vo);
	//根据保存的截取链接判断当前数据是否存在  存在则不保存
	int selectDownHrefVoByHref(String downHref);
	//查找所有电影信息
	List<MovieVo> selectAllMovieVo(Map<String, Object> paramMap);
	//查找所有总数
	int selectMovieVoCount(Map<String, Object> map);
	//根据电影id查看电影详情
	MovieVo getMovieDetail(String movieId);
	//拿到所点category为1并且同步标志N的影片
	List<MovieVo> getAllMovie(Map<String, Object> paramMap);
	//根据影片id更新影片信息，更新固定字段，批量
	void updateMovieInfoByMovieIdList(Map<String,Object> paramMap);
	//根据影片id更新影片信息，更新已存在值得对应字段
	void updateMovieByList(@Param("updateMovieList")List<MovieVo> updateMovieList);
}
