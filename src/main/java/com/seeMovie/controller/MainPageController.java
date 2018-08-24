package com.seeMovie.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.seeMovie.pojo.VisitUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.druid.util.StringUtils;
import com.seeMovie.common.utils.IpInfoUtils;
import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.LoginService;
import com.seeMovie.service.MovieService;
/**
 * 
 * @author 		mym
 * @date   		2018年7月6日下午10:11:40
 * @describe	进入主页
 * 
 */
@Controller
@RequestMapping("/mainPage")
public class MainPageController {
	@Autowired
	MovieService movieService;
	@Autowired
	LoginService loginService;
	/**
	 * 进入主页面
	 */
	@RequestMapping("/mainPage")
	public ModelAndView toMainPage(PagingUtil pagingUtil,String category,String rowNum,String showType,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		//封装参数
		Map<String,Object> map = new HashMap<>();
		try {
			//初次登录时将当前登录人信息存入信息表中
			if(StringUtils.isEmpty(category) && StringUtils.isEmpty(rowNum)&& StringUtils.isEmpty(showType)){
				//首次进入网站时将当前登录IP等信息存起来
				VisitUserInfoVo vo = new VisitUserInfoVo();
				vo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				vo.setIp(IpInfoUtils.getVisitIp(request));
				vo.setIpAddress(IpInfoUtils.getIpAddress(vo.getIp()));
				vo.setHostName(IpInfoUtils.getHostName(request));
				vo.setSource("1");
				vo.setUsername("访客");
				vo.setDate(new Date());
				loginService.insertVisitUserInfoVo(vo);
			}
			//根据影片类型查找对应影片
			map.put("category", category);
			//根据影片分类查找对应影片   (喜剧爱情)
			//每行显示影片数目
			map.put("rowNum",!StringUtils.isEmpty(rowNum)?Integer.valueOf(rowNum):4);//默认每行显示4个
			//展示方式
			if(!StringUtils.isEmpty(showType)){
				map.put("showType",showType);
				mv.addObject("showType",showType);
			}else{
				map.put("showType","picture");
				mv.addObject("showType","picture");
			}
			map = movieService.selectMovieInfoByParam(pagingUtil,map);
			mv.addObject("movieList",map.get("movieList"));
			mv.addObject("movieCategoryList",map.get("movieCategoryList"));//影片分类集合
			mv.addObject("pagingUtil",map.get("pagingUtil"));
			//以下参数在再次查询时有用 默认记住用户上一次查询的条件
			mv.addObject("category",category);
			mv.addObject("rowNum",!StringUtils.isEmpty(rowNum)?Integer.valueOf(rowNum):4);
			mv.setViewName("homePage/mainPage");
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
	 * 
	 */
	@RequestMapping("/movieDetail")
	public ModelAndView movieDetail(String movieId){
		ModelAndView mv = new ModelAndView();
		try {
			//查找所有电影
			MovieVo movieVo = movieService.getMovieDetail(movieId);
			mv.addObject("movie", movieVo);
			mv.setViewName("homePage/movieDetail");
		} catch (Exception e) {
			mv.setViewName("error");
		}
		return mv;
	}
}
