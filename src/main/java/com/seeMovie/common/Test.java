package com.seeMovie.common;

import com.seeMovie.common.utils.IpInfoUtils;

public class Test {
public static void main(String[] args) {
	/*String test = "产　　地　美国 ";
	System.out.println(test.trim().length()>4?test.trim().substring(4,test.trim().length()).trim():"");
	System.out.println("产　　地　美国".replaceAll("　　", ""));
	System.out.println("产　　地　美国".replaceAll("　　","").replaceAll("　", ""));
	System.out.println("产　　地　美国".replaceAll("　　","").replaceAll("　", "").trim().length()>2?"产　　地　美国".replaceAll("　　","").replaceAll("　", "").trim().substring(2,"产　　地　美国".replaceAll("　　","").replaceAll("　", "").trim().length()).trim():"");*/
	IpInfoUtils.getIpAddress("218.192.3.42");
}
}
