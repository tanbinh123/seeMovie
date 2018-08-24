// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IpInfoUtils.java

package com.seeMovie.common.utils;

import javax.servlet.http.HttpServletRequest;

public class IpInfoUtils
{

	public IpInfoUtils()
	{
	}

	public static String getVisitIp(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		try
		{
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("PRoxy-Client-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("WL-Proxy-Client-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getRemoteAddr();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ip;
	}

	public static String getHostName(HttpServletRequest request)
	{
		String hostName = "";
		try
		{
			hostName = request.getRemoteHost();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hostName;
	}
}
