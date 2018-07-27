package com.seeMovie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 
 * @author		mym
 * @date   		2018年7月6日下午10:09:40
 * @describe	系统主要入口
 * 
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.seeMovie.mapper")
public class SeeMovieApplication extends SpringBootServletInitializer {
	//这是为打war部署做准备 包括extends的SpringBootServletInitializer都是为打war包部署时使用
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SeeMovieApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SeeMovieApplication.class,args);
	}

}
