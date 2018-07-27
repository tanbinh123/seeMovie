package com.seeMovie.common.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.seeMovie.service.MovieService;

/**
 * 
 * @author	 	mym
 * @date   		2018年7月21日下午1:36:45
 * @describe 	定时更新影片信息  例如影片类型、影片分类等
 */
@Component
public class UpdateMovieInfoTimer {
	@Autowired
	MovieService movieService;
	/**
	 * 
	 * @author  	mym
	 * @date        2018年7月21日下午3:58:35
	 * @return      void
	 * @describe    更新影片类型(电影/电视剧)
	 */
	@Scheduled(cron=" 0 45 3 * * ?") //每天凌晨3点45执行一次  0 45 3 * * ?
	public void UpdateMovieCategoryInfoTimer(){ 
		movieService.UpdateMovieCategoryInfoTimer();
	}
	
	@Scheduled(cron="0 55 3 * * ?") //每天凌晨3点55执行一次  0 55 3 * * ?
	public void UpdateMovieImgUrlInfoTimer(){ 
		movieService.UpdateMovieImgUrlInfoTimer();
	}
}
