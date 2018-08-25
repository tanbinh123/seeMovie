package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

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
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?Integer.valueOf(pageNumber)+1:0);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize)+1:20);
			returnMap.put("menuName",menuName);
			returnMap.put("menuParentId",menuParentId);
			returnMap = menuService.selectMenuListByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
