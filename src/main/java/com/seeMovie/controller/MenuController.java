package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.seeMovie.common.utils.JsonData;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.MenuVo;
import com.seeMovie.service.LoginService;
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
import java.util.*;
import java.util.List;

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
	LoginService loginService;
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
		try{
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			model.addObject("userName","admin");
			model.addObject("menuName","菜单管理");
			model.setViewName("theBackGround/systemPage/menu");
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * @author      mym
	 * @date        2018/8/29 0029 9:11
	 * @describe    多条件查询菜单数据集合
	 * @version     V1.0
	 * @param       [menuParentId, menuName, pageSize, pageNumber]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	@RequestMapping("/selectMenuListByParam")
	@ResponseBody
	public Map<String,Object> selectMenuListByParam(String menuParentId,String menuName,String pageSize,String pageNumber){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?(Integer.valueOf(pageNumber)/Integer.valueOf(pageSize))+1:1);
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
	 * @describe    新增或者编辑菜单
	 * @version     V1.0
	 * @param
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/addOrUpdateMenu")
	@ResponseBody
	public JsonData addOrUpdateMenu(String vo){
		JsonData jsonData = new JsonData();
		try {
			if(!StringUtils.isEmpty(vo)){
				MenuVo menuVo = JSON.parseObject(vo,MenuVo.class);
				if(StringUtils.isEmpty(menuVo.getMenuId())){//新增
					menuVo.setMenuId(UUID.randomUUID().toString().replaceAll("-",""));
					menuVo.setMenuFlag("Y");
					menuVo.setCreateDate(new Date());
					menuService.insertMenuVo(menuVo);
				}else{//编辑
					menuVo.setUpdateDate(new Date());
					menuService.updateMenuVo(menuVo);
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
	 * @date        2018/9/4 0004 10:44
	 * @describe    查询所有的父级菜单
	 * @version     V1.0
	 * @param       []
	 * @return      java.util.List<com.seeMovie.pojo.MenuVo>
	*/
	@RequestMapping("/selectAllParentMenu")
	@ResponseBody
	public List<MenuVo> selectAllParentMenu(){
		List<MenuVo> returnList= new ArrayList<>();
		try {
			returnList = menuService.selectAllParentMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * @author      mym
	 * @date        2018/9/8 0008 16:05
	 * @describe    根据菜单id删除菜单
	 * @version     V1.0
	 * @param       [ids]
	 * @return      com.seeMovie.common.utils.JsonData
	*/
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public JsonData deleteMenu(String ids){
		JsonData jsonData= new JsonData();
		try {
			int returnType = menuService.deleteMenu(!StringUtils.isEmpty(ids)?ids.split(","):null);
			if(returnType == 0){
				jsonData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonData;
	}
}
