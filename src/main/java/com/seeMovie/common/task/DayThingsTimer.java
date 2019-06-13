package com.seeMovie.common.task;

import com.seeMovie.pojo.DayThingsVo;
import com.seeMovie.service.DayThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 
 * @author	 	mym
 * @date   		2018年7月21日下午1:36:45
 * @describe 	定时更新影片信息  例如影片类型、影片分类等
 */
@Component
public class DayThingsTimer {
	@Autowired
	DayThingsService dayThingsService;
	/* *
	* @author :     mym
	* @date Date :  2019/6/13 17:51
	* @version :    V1.0
	* @describe :   获取历史上的今天的数据
	* @param :      
	* @return :     
	*/
	//@Scheduled(cron="0/30 * * * * ? ")
	public void GetDayThings(){
		try {
			String today = "";
			//String url = "http://140.143.182.111:33550/port/history?dispose=detail&key=jiahengfei&month=6&day=13";
			String url = "http://140.143.182.111:33550/port/history?dispose=detail&key=jiahengfei";
			for (int i =1;i<=12;i++){
				if(i == 2){//二月  最多29
					for (int j =1;j<=29;j++){
						today = "0"+i+"-"+(j<10?"0"+j:j);
						url = url + "&month="+i +"&day="+j;
						if(checkDays(today)==true){
							InsertDayThings(url,today);
							url = "http://140.143.182.111:33550/port/history?dispose=detail&key=jiahengfei";
						}
					}
				}
				if(i==4 || i==6 || i==9 || i ==12){
					for (int j =1;j<=30;j++){//四、六、九、十二
						today = (i<10?"0"+i:i)+"-"+(j<10?"0"+j:j);
						url = url + "&month="+i +"&day="+j;
						if(checkDays(today)==true){
							InsertDayThings(url,today);
							url = "http://140.143.182.111:33550/port/history?dispose=detail&key=jiahengfei";
						}
					}
				}
				if(i==1 || i==3 || i==5 || i ==7 || i==8 || i==10 || i==11){
					for (int j =1;j<=31;j++){//一、三、五、七、八、十、十一
						today = (i<10?"0"+i:i)+"-"+(j<10?"0"+j:j);
						url = url + "&month="+i +"&day="+j;
						if(checkDays(today)==true){
							InsertDayThings(url,today);
							url = "http://140.143.182.111:33550/port/history?dispose=detail&key=jiahengfei";
						}
					}
				}
			}
		   // System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkDays(String today) {
		return dayThingsService.checkDays(today);
	}

	private void InsertDayThings(String webUrl, String today) {
		try{
			DayThingsVo dayThingsVo = new DayThingsVo();
			URL url = new URL(webUrl);
			// 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中
			connection.connect();//建立连接
			// 获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {// 循环读取流
				sb.append(line);
			}
			br.close();// 关闭流
			connection.disconnect();// 断开连接

			dayThingsVo.setId(UUID.randomUUID().toString().replaceAll("-",""));
			dayThingsVo.setDayId(today);
			dayThingsVo.setDayThings(sb.toString());
			dayThingsService.insertDayThings(dayThingsVo);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
