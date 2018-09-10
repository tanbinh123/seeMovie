package com.seeMovie.mapper;

import com.seeMovie.pojo.MovieVo;
import com.seeMovie.pojo.WebLinksVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
public interface MovieMapper {
	int insert(MovieVo vo);
	
    List<MovieVo> selectAll();
	//保存当前电影资源
	void insertDownLoadHrefByVo(MovieVo vo);
	//根据保存的截取链接判断当前数据是否存在  存在则不保存
	int selectDownLoadHrefVoByLoadHref(String downLoadHref);
	//查找所有电影信息
	List<MovieVo> selectAllMovieVo(Map<String, Object> paramMap);
	//查找所有总数
	int selectMovieVoCount(Map<String, Object> map);
	//根据电影id查看电影详情
	MovieVo getMovieDetail(String movieId);
	//拿到所有category为1并且同步标志N的影片
	List<MovieVo> getAllMovie(Map<String, Object> paramMap);
	//根据影片id更新影片信息，更新固定字段，批量
	void updateMovieInfoByMovieIdList(Map<String,Object> paramMap);
	//根据影片id更新影片信息，更新已存在值得对应字段
	void updateMovieByList(@Param("updateMovieList")List<MovieVo> updateMovieList);
	//每次查询两条网站初始化链接
	List<WebLinksVo> getWebLinksVo();
	//将当前已经爬取过的网站爬取状态改为Y
	void updateWebLinks(WebLinksVo webLinksVo);
	//查找所有电影生产国家
	List<String> selectAlCountryList();
	//单条更新图片imgUrl
	void updateMovieImgUrlByMovieVo(MovieVo movieVo);
	//查询符合条件的影片总数
    int selectCountMovieListByParam(Map<String, Object> returnMap);
	//查询符合条件的影片信息
	List<MovieVo> selectMovieListByParam(Map<String, Object> returnMap);
	//更新影片信息
	void updateMovieVo(MovieVo movieVo);
	//删除影片信息
	void deleteMovieVo(@Param("ids") String[] ids);
}
