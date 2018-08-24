package com.seeMovie.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.util.StringUtils;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 
 * @author mym
 * @date
 * @describe ip操作相关工具类
 */
public class IpInfoUtils {
	private static String url="http://ip.taobao.com/service/getIpInfo.php";
	/**
	 * 
	 * @author   mym
	 * @date     2018-08-24 17:01:13
	 * @describe 获取ip
	 * @return   request
	 */
	public static String getVisitIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		try {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("PRoxy-Client-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("WL-Proxy-Client-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip))
				ip = request.getRemoteAddr();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * 
	 * @author   mym
	 * @date 	 2018-08-24 17:01:27
	 * @describe 获取hostName
	 * @return   request
	 */
	public static String getHostName(HttpServletRequest request) {
		String hostName = "";
		try {
			hostName = request.getRemoteHost();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hostName;
	}

	/**
	 * 
	 * @author	 mym
	 * @date	 2018-08-24 17:01:27
	 * @describe 通过ip获取实际地址 省市区
	 * @return 	 ip
	 */
	public static String getIpAddress(String ip) {
		String ipAddress = "";
		try {
			if(!StringUtils.isEmpty(ip) && !ip.equals("127.0.0.1")){
				// 设置此类是否应该自动执行 HTTP重定向（响应代码为 3xx 的请求）。
				HttpURLConnection.setFollowRedirects(false);
				// 到URL所引用的远程对象的连接
				HttpURLConnection conn = (HttpURLConnection) new URL(url+"?ip="+ip).openConnection();
				//设置请求超时
				conn.setConnectTimeout(5000);
				// 设置URL请求的方法，GET POST HEAD OPTIONS PUT DELETE TRACE
				// 以上方法之一是合法的，具体取决于协议的限制。
				conn.setRequestMethod("POST");
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));// 往对端写完数据对端服务器返回数据
				// 以BufferedReader流来读取
				StringBuffer buffer = new StringBuffer();
				while (reader.readLine() != null) {
					buffer.append(reader.readLine());
				}
				reader.close();
				if(buffer != null){
					//{"code":0,"data":{"ip":"218.192.3.42","country":"中国","area":"","region":"广东","city":"广州","county":"XX","isp":"教育网","country_id":"CN","area_id":"","region_id":"440000","city_id":"440100","county_id":"xx","isp_id":"100027"}}
					String ipInfo = buffer.toString();
					String[] ipInfoArr = new String[]{};
					if(ipInfo.contains("country") && ipInfo.contains("county")){
						ipInfo = ipInfo.substring(ipInfo.lastIndexOf("{")+1,ipInfo.lastIndexOf("}"));
						ipInfoArr = ipInfo.split(",");
						String countryStr = ipInfoArr[1];//"country":"中国"
						String region = ipInfoArr[3];//"country":"中国"
						String city = ipInfoArr[4];//"city":"广州"
						countryStr = countryStr.substring(countryStr.indexOf(":")+2, countryStr.length()-1);
						region = region.substring(region.indexOf(":")+2, region.length()-1);
						city = city.substring(city.indexOf(":")+2, city.length()-1);
						ipAddress = countryStr+"/"+region+"/"+city;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipAddress;
	}
}
