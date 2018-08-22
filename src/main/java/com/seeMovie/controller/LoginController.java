package com.seeMovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;

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
	public ModelAndView login(ModelAndView model,String userName,String password){
		//因为后台管理不对外开放  所以此处进行伪登录
		if(StringUtils.isEmpty(userName)&&StringUtils.isEmpty(password)&&userName.equals("admin")&&password.equals("admin")){
			model.setViewName("theBackGround/index");	
		}else{
			model.addObject("errorInfo","用户名或者密码错误请重新输入！");
			model.setViewName("theBackGround/login");
		}
		return model;
	}
}
