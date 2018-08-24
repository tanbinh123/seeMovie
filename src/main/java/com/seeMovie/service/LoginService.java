
package com.seeMovie.service;

import com.seeMovie.pojo.VisitUserVo;

import java.util.List;
/**
 * @author      mym
 * @date        2018/8/24 0024 9:53
 * @describe    获取登陆者信息的接口
 * @version     V1.0
 * @param
 * @return      
*/
public interface LoginService
{

	public  void insertVisitUserVo(VisitUserVo visituservo);

	public  List selectTheLastTenVisitsByUserName(String s);

	public  int selectAllVisitNumsByUserName(String s);
}
