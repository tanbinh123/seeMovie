package com.seeMovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author 		mym
 * @date   		2018年8月1日下午6:20:15
 * @describe    后端框架主页相关操作	
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年7月31日下午3:19:31
	 * @describe	后端桌面
	 * @return		String
	 *
	 */
	@RequestMapping("/welcome")
	public String welcome(){
			return "theBackground/welcome";
	}
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年8月1日下午6:23:34
	 * @describe	菜单管理
	 * @return		String
	 *
	 */
	@RequestMapping("/menu")
	public String menu(){
			return "theBackground/menu";
	}
}
