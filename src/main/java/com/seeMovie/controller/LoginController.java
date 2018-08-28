package com.seeMovie.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.VisitUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.common.utils.IpInfoUtils;
import com.seeMovie.service.LoginService;

/**
 * 
 * @author 		mym
 * @date   		2018年7月31日下午3:15:27
 * @describe	登录相关操作
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	LoginService loginService;
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年7月31日下午3:19:31
	 * @describe	
	 * @return		String
	 *
	 */
	@RequestMapping("/toLoginPage")
	public String goToLoginPage(){
			return "theBackGround/login";
	}
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年7月31日下午10:59:09
	 * @describe	登录成功进入主页否则重定向到登录页
	 * @return		String
	 *
	 */
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView model,String userName,String password,HttpServletRequest request){
		//因为后台管理不对外开放  所以此处进行伪登录
		try {
			//查询系统当日首页访问量   访问ip去重总数
			int totalVisitOfToday = loginService.selectTotalVisitOfToday();
			model.addObject("totalVisitOfToday",totalVisitOfToday);
			//查找所有菜单数据集合
			List<MenuQueryVo> menuQueryVoList = loginService.selectAllMenuList();
			model.addObject("menuQueryVoList",menuQueryVoList);
			if(!StringUtils.isEmpty(userName)&&!StringUtils.isEmpty(password)&&userName.equals("admin")&&password.equals("admin")){
				//登录成功后将当前登录IP等信息存起来
				VisitUserInfoVo vo = new VisitUserInfoVo();
				vo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				vo.setIp(IpInfoUtils.getVisitIp(request));
				vo.setIpAddress(IpInfoUtils.getIpAddress(vo.getIp()));
				vo.setHostName(IpInfoUtils.getHostName(request));
				vo.setSource("2");
				vo.setUsername("admin");
				vo.setVisitDate(new Date());
				loginService.insertVisitUserInfoVo(vo);
				//查询后端用户最近十次登录信息及总的登录次数
				List<VisitUserInfoVo> VisitUserInfoVoList = loginService.selectTheLastTenVisitsByUserName("admin");//其实应该用用户id查询
				int loginNum = loginService.selectAllVisitNumsByUserName("admin");
				model.addObject("userName","admin");
				model.addObject("loginNum",loginNum);
				model.addObject("VisitUserInfoVoList",VisitUserInfoVoList);
				model.setViewName("theBackGround/index");	
			}else{
				model.addObject("errorInfo","用户名或者密码错误请重新输入！");
				model.setViewName("theBackGround/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	/**
	 * 
	 * @author		mym
	 * @date     	2018年8月24日下午9:30:09
	 * @return   	Map<String,Object>
	 * @describe   查询当前登录这最近10次登录信息
	 *
	 */
	@RequestMapping("/selectLoginListByUserName")
	@ResponseBody
	public Map<String,Object> selectLoginListByUserName(String userName){
		Map<String,Object> returnMap = new HashMap<>();
		try {
			if(!StringUtils.isEmpty(userName) && userName.equals("admin")){
				//查询后端用户最近十次登录信息及总的登录次数
				List<VisitUserInfoVo> VisitUserInfoVoList = loginService.selectTheLastTenVisitsByUserName("admin");//其实应该用用户id查询
				if(VisitUserInfoVoList != null){
					returnMap.put("total", VisitUserInfoVoList.size());
					returnMap.put("rows", VisitUserInfoVoList);
				}else{
					returnMap.put("total",0);
					returnMap.put("rows",null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}
