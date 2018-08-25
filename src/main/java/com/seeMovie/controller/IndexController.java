package com.seeMovie.controller;


import com.seeMovie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author      mym
 * @date        2018/8/25 0025 14:09
 * @describe	 后台主页控制器
 * @version     V1.0
 * @param       
 * @return      
*/
@Controller
@RequestMapping("/index")
public class IndexController {
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
	@RequestMapping("/index")
	public ModelAndView goToindex(ModelAndView model,String userName){
		try {
			int loginNum = loginService.selectAllVisitNumsByUserName(userName);
			model.addObject("loginNum",loginNum);
			model.addObject("userName",userName);
			model.setViewName("theBackGround/index");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
