package com.seeMovie.service;

import com.seeMovie.pojo.VisitUserInfoVo;

import java.util.List;

/**
 * @author mym
 * @date 2018/8/24 0024 9:53
 * @describe 获取登陆者信息的接口
 * @version V1.0
 * @param
 * @return
 */
public interface LoginService {

	public void insertVisitUserInfoVo(VisitUserInfoVo VisitUserInfoVo);

	public List<VisitUserInfoVo> selectTheLastTenVisitsByUserName(String userName);

	public int selectAllVisitNumsByUserName(String userName);
	//查询系统当日首页访问量   访问ip去重总数
    int selectTotalVisitOfToday();
}
