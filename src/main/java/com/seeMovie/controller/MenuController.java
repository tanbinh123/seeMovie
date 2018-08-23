package com.seeMovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	/**
	 * 
	 * @author 		mym
	 * @date   		2018年7月31日下午3:19:31
	 * @describe	
	 * @return		String
	 *
	 */
	@RequestMapping("/toMenu")
	public String goToLoginPage(){
			return "theBackGround/systemPage/menu";
	}
}
