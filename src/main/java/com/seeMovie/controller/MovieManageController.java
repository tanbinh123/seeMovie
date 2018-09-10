package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.seeMovie.common.utils.JsonData;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.pojo.WebLinksVo;
import com.seeMovie.service.LoginService;
import com.seeMovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author      mym
 * @date        2018/9/4 0004 11:33
 * @describe    影片相关操作controller
 * @version     V1.0
 * @param       影片相关操作controller
 * @return      
*/
@Controller
@RequestMapping("/movieManage")
public class MovieManageController {
	@Autowired
	MovieService movieService;
	@Autowired
	LoginService loginService;
	/**
	 * @author      mym
	 * @date        2018/9/4 0004 11:34
	 * @describe    进入影片管理首页
	 * @version     V1.0
	 * @param
	 * @return      org.springframework.web.servlet.ModelAndView
	*/
	@RequestMapping("/toMovieManagePage")
	public ModelAndView goToMovieManagePage(ModelAndView model){
		try{
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			model.addObject("userName","admin");
			model.addObject("menuName","影片管理");
			model.setViewName("theBackGround/actionPage/movieManagePage");
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * @author      mym
	 * @date        2018/9/4 0004 11:35
	 * @describe    [pageSize, pageNumber] 多条件查询影片信息
	 * @version     V1.0
	 * @param       [pageSize, pageNumber]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	@RequestMapping("/selectAllMovieByParam")
	@ResponseBody
	public Map<String,Object> selectAllMovieByParam(MovieVo movieVo,String pageSize, String pageNumber){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?(Integer.valueOf(pageNumber)/Integer.valueOf(pageSize))+1:1);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize):20);
			returnMap.put("movieVo",movieVo);
			returnMap = movieService.selectAllMovieByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * @author      mym
	 * @date        2018/9/10 0010 14:24
	 * @describe    新增或者编辑影片信息
	 * @version     V1.0
	 * @param       [vo]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/addOrUpdateMovie")
	@ResponseBody
	public JsonData addOrUpdateMovie(String vo){
		JsonData jsonData = new JsonData();
		try {
			if(!StringUtils.isEmpty(vo)){
				MovieVo movieVo = JSON.parseObject(vo,MovieVo.class);
				if(StringUtils.isEmpty(movieVo.getMovieId())){
					movieVo.setMovieId(UUID.randomUUID().toString().replaceAll("-",""));
					movieService.insertMovieVo(movieVo);
				}else{
					movieService.updateMovieVo(movieVo);
				}
				jsonData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
	/**
	 * @author      mym
	 * @date        2018/9/10 0010 14:24
	 * @describe    删除影片信息
	 * @version     V1.0
	 * @param       [ids]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/deleteMovie")
	@ResponseBody
	public JsonData deleteMovie(String ids){
		JsonData jsonData= new JsonData();
		try {
			int returnType = movieService.deleteMovieVo(!StringUtils.isEmpty(ids)?ids.split(","):null);
			if(returnType == 0){
				jsonData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
