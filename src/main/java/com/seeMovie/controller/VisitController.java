package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.service.LoginService;
import com.seeMovie.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author      mym
 * @date        2018/8/29 0029 9:03
 * @describe    访客管理controller
 * @version     V1.0
 * @param       访客管理controller
 * @return      
*/
@Controller
@RequestMapping("/visit")
public class VisitController {
	@Autowired
	LoginService loginService;
	@Autowired
	VisitService visitService;
	/**
	 * @author      mym
	 * @date        2018/8/29 0029 9:04
	 * @describe    [model]
	 * @version     V1.0
	 * @param       [model]
	 * @return      org.springframework.web.servlet.ModelAndView
	*/
	@RequestMapping("/toVisitPage")
	public ModelAndView goToLoginPage(ModelAndView model){
		try{
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			model.addObject("userName","admin");
			model.addObject("menuName","访客管理");
			model.setViewName("theBackGround/visitPage/visitPage");
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * @author      mym
	 * @date        2018/8/29 0029 9:12
	 * @describe    根据条件查询对应访问者的ip信息
	 * @version     V1.0
	 * @param       [menuParentId, menuName, pageSize, pageNumber]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	@RequestMapping("/selectAllVisitInfoByParam")
	@ResponseBody
	public Map<String,Object> selectAllVisitInfoByParam(String pageSize,String pageNumber){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?(Integer.valueOf(pageNumber)/Integer.valueOf(pageSize))+1:1);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize):20);
			returnMap = visitService.selectAllVisitInfoByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
