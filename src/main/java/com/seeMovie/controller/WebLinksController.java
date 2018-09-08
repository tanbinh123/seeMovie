package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.seeMovie.common.utils.JsonData;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.WebLinksVo;
import com.seeMovie.service.LoginService;
import com.seeMovie.service.WebLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author      mym
 * @date        2018/9/5 0005 18:30
 * @describe    待爬取网站管理
 * @version     V1.0
 * @param       待爬取网站管理
 * @return      
*/
@Controller
@RequestMapping("/webLinks")
public class WebLinksController {
	@Autowired
	LoginService loginService;
	@Autowired
	WebLinksService webLinksService;
	/**
	 * @author      mym
	 * @date        2018/9/5 0005 18:30
	 * @describe    进入待爬取网站管理首页
	 * @version     V1.0
	 * @param       [model]
	 * @return      org.springframework.web.servlet.ModelAndView
	*/
	@RequestMapping("/toWebLinksPage")
	public ModelAndView goToWebLinksPage(ModelAndView model){
		try{
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			model.addObject("userName","admin");
			model.addObject("menuName","待爬取网站列表");
			model.setViewName("theBackGround/actionPage/webLinksPage");
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * @author      mym
	 * @date        2018/9/5 0005 18:34
	 * @describe    多条件查询符合条件的待爬取网站信息
	 * @version     V1.0
	 * @param       [movieVo, pageSize, pageNumber]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	@RequestMapping("/selectAllWebLinksByParam")
	@ResponseBody
	public Map<String,Object> selectAllWebLinksByParam(WebLinksVo webLinksVo, String pageSize, String pageNumber){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?(Integer.valueOf(pageNumber)/Integer.valueOf(pageSize))+1:1);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize):20);
			returnMap.put("webLinksVo",webLinksVo);
			returnMap = webLinksService.selectAllWebLinksByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * @author      mym
	 * @date        2018/9/5 0005 18:59
	 * @describe    新增待爬取网站链接
	 * @version     V1.0
	 * @param       [vo]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/addOrUpdateWebLink")
	@ResponseBody
	public JsonData addOrUpdateWebLink(String vo){
		JsonData jsonData = new JsonData();
		try {
			if(!StringUtils.isEmpty(vo)){
				WebLinksVo webLinksVo = JSON.parseObject(vo,WebLinksVo.class);
				if(StringUtils.isEmpty(webLinksVo.getWebId())){
					webLinksVo.setWebId(UUID.randomUUID().toString().replaceAll("-",""));
					webLinksVo.setCrawlFlag("Y");
					webLinksVo.setInsertDate(new Date());
					webLinksService.insertWebLinkVo(webLinksVo);
				}else{
					webLinksService.updateWebLinkVo(webLinksVo);
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
	 * @date        2018/9/5 0005 19:40
	 * @describe    根据主键id编辑待爬取网站信息
	 * @version     V1.0
	 * @param       [vo]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/updateWebLink")
	@ResponseBody
	public JsonData updateWebLink(String vo){
		JsonData jsonData = new JsonData();
		try {
			if(!StringUtils.isEmpty(vo)){
				WebLinksVo webLinksVo = JSON.parseObject(vo,WebLinksVo.class);
				webLinksVo.setUpdateDate(new Date());
				webLinksService.updateWebLinkVo(webLinksVo);
				jsonData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
	/**
	 * @author      mym
	 * @date        2018/9/8 0008 16:43
	 * @describe    [ids] 根据待爬取网站id删除对应数据
	 * @version     V1.0
	 * @param       [ids]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/deleteWebLink")
	@ResponseBody
	public JsonData deleteWebLink(String ids){
		JsonData jsonData= new JsonData();
		try {
			int returnType = webLinksService.deleteWebLink(!StringUtils.isEmpty(ids)?ids.split(","):null);
			if(returnType == 0){
				jsonData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
