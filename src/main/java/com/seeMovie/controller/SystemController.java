package com.seeMovie.controller;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.service.LoginService;
import com.seeMovie.service.SystemLogService;
import com.seeMovie.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author      mym
 * @date        2018/9/8 0008 13:39
 * @describe    系统运行日志相关管理
 * @version     V1.0
 * @param       系统运行日志相关管理
 * @return      
*/
@Controller
@RequestMapping("/system")
public class SystemController {
	@Autowired
	LoginService loginService;
	@Autowired
	SystemLogService systemLogService;
	/**
	 * @author      mym
	 * @date        2018/9/8 0008 13:40
	 * @describe    进入系统日志管理页面
	 * @version     V1.0
	 * @param       [model]
	 * @return      org.springframework.web.servlet.ModelAndView
	*/
	@RequestMapping("/toSystemPage")
	public ModelAndView goToSystemPage(ModelAndView model){
		try{
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			model.addObject("userName","admin");
			model.addObject("menuName","系统日志");
			model.setViewName("theBackGround/systemPage/systemPage");
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * @author      mym
	 * @date        2018/9/8 0008 13:41
	 * @describe    [pageSize, pageNumber, source, ip, visitTimeStart, visitTimeEnd]多条件查询系统日志
	 * @version     V1.0
	 * @param       [pageSize, pageNumber, source, ip, visitTimeStart, visitTimeEnd]
	 * @return      java.util.Map<java.lang.String,java.lang.Object>
	*/
	@RequestMapping("/selectAllSystemLogInfoByParam")
	@ResponseBody
	public Map<String,Object> selectAllSystemLogInfoByParam(String pageSize,String pageNumber,String startTime,String endTime){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			//封装查询参数
			returnMap.put("currentPage", !StringUtils.isEmpty(pageNumber)?(Integer.valueOf(pageNumber)/Integer.valueOf(pageSize))+1:1);
			returnMap.put("pageSize",!StringUtils.isEmpty(pageSize)?Integer.valueOf(pageSize):20);
			returnMap.put("startTime",startTime);
			returnMap.put("endTime",endTime);
			returnMap = systemLogService.selectAllSystemLogInfoByParam(returnMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
