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
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SeeMovieApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SeeMovieApplication.class,args);
	}
	
	//destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
	//@Bean(destroyMethod = "close")
	/*public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/see_movie?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
		dataSource.setUsername("root");//用户名
		dataSource.setPassword("root");//密码
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setInitialSize(2);//初始化时建立物理连接的个数
		dataSource.setMaxActive(20);//最大连接池数量
		dataSource.setMinIdle(0);//最小连接池数量
		dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
		dataSource.setValidationQuery("SELECT 1");//用来检测连接是否有效的sql
		dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
		dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
		dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
		return dataSource;
	}*/

}
