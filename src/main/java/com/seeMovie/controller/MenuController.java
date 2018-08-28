package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.seeMovie.common.utils.JsonData;
import com.seeMovie.pojo.MenuVo;
import com.seeMovie.service.MenuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author      mym
 * @date        2018/8/23 0023 19:44
 * @describe    进入菜单主页
 * @version     V1.0
 * @param
 * @return      
*/
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	MenuService menuService;
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年7月31日下午3:19:31
	 * @describe	
	 * @return		String
	 *
	 */
	@RequestMapping("/toMenu")
	public ModelAndView goToLoginPage(ModelAndView model){
		model.addObject("userName","admin");
		model.addObject("menuName","菜单管理");
		model.setViewName("theBackGround/systemPage/menu");
		return model;
	}

	@RequestMapping("/selectMenuListByParam")
	@ResponseBody
	public Map<String,Object> selectMenuListByParam(String menuParentId,String menuName,String pageSize,String pageNumber){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?Integer.valueOf(pageNumber)+1:1);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize):20);
			returnMap.put("menuName",menuName);
			returnMap.put("menuParentId",menuParentId);
			returnMap = menuService.selectMenuListByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
	/**
	 * @author      mym
	 * @date        2018/8/28 0028 20:56
	 * @describe    新增菜单
	 * @version     V1.0
	 * @param
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/insertMenu")
	@ResponseBody
	public JsonData insertMenu(String vo){
		JsonData jsonData = new JsonData();
		try {
			if(!StringUtils.isEmpty(vo)){
				MenuVo menuVo = JSON.parseObject(vo,MenuVo.class);
				menuVo.setMenuId(UUID.randomUUID().toString().replaceAll("-",""));
				menuVo.setMenuFlag("Y");
				menuVo.setCreateDate(new Date());
				menuService.insertMenuVo(menuVo);
				jsonData.setState(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
