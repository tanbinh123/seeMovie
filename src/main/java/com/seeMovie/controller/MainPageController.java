package com.seeMovie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author mym
 * @date   2018年7月6日下午10:11:40
 * 进入主页
 */
@Controller
@RequestMapping("/mainPage")
public class MainPageController {
	/**
	 * 进入主页面
	 */
	@RequestMapping("/mainPage")
	public String toMainPage(){
		return "mainPage";
	}
}
