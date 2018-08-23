package com.seeMovie.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.common.utils.IpInfoUtils;
import com.seeMovie.pojo.VisitUserVo;
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
		if(!StringUtils.isEmpty(userName)&&!StringUtils.isEmpty(password)&&userName.equals("admin")&&password.equals("admin")){
			//登录成功后将当前登录IP等信息存起来
			VisitUserVo vo = new VisitUserVo();
			vo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			vo.setIp(IpInfoUtils.getVisitIp(request));
			vo.setHostName(IpInfoUtils.getHostName(request));
			vo.setSource("2");
			vo.setUsername("admin");
			vo.setDate(new Date());
			loginService.insertVisitUserVo(vo);
			//查询后端用户最近十次登录信息及总的登录次数
			List<VisitUserVo> visitUserVoList = loginService.selectTheLastTenVisitsByUserName("admin");//其实应该用用户id查询
			int loginNum = loginService.selectAllVisitNumsByUserName("admin");
			model.addObject("userName","admin");
			model.addObject("loginNum",loginNum);
			model.addObject("visitUserVoList",visitUserVoList);
			model.setViewName("theBackGround/index");	
		}else{
			model.addObject("errorInfo","用户名或者密码错误请重新输入！");
			model.setViewName("theBackGround/login");
		}
		return model;
	}
}
