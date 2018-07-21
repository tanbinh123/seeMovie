package com.seeMovie.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.MovieService;
/**
 * 
 * @author mym
 * @date   2018年7月6日下午10:11:40
 * 进入主页
 */
@Controller
@RequestMapping("/mainPage")
public class MainPageController {
	@Autowired
	MovieService movieService;
	/**
	 * 进入主页面
	 */
	@RequestMapping("/mainPage")
	public ModelAndView toMainPage(PagingUtil pagingUtil,String category){
		ModelAndView mv = new ModelAndView();
		//封装参数
		Map<String,Object> map = new HashMap<>();
		try {
			//根据影片类型查找对应电影
			map.put("category", category);
			map = movieService.selectMovieInfoByParam(pagingUtil,map);
			mv.addObject("movieList",map.get("movieList"));
			mv.addObject("pagingUtil",map.get("pagingUtil"));
			mv.addObject("category",category);
			mv.setViewName("mainPage");
		} catch (Exception e) {
			mv.setViewName("error");
		}
		return mv;
	}
	/**
	 * 
	 * @author		mym
	 * @date     2018年7月18日下午10:10:09
	 * @return   ModelAndView
	 * @describe 根据电影id查看电影详情
	 */
	@RequestMapping("/movieDetail")
	public ModelAndView movieDetail(String movieId){
		ModelAndView mv = new ModelAndView();
		try {
			//查找所有电影
			MovieVo movieVo = movieService.getMovieDetail(movieId);
			mv.addObject("movie", movieVo);
			mv.setViewName("movieDetail");
		} catch (Exception e) {
			mv.setViewName("error");
		}
		return mv;
	}
}
