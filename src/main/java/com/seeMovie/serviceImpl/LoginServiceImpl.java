
package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.VisitUserVoMapper;
import com.seeMovie.pojo.VisitUserVo;
import com.seeMovie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	@Autowired
	VisitUserVoMapper visitUserVoMapper;

	public LoginServiceImpl()
	{
	}

	public void insertVisitUserVo(VisitUserVo vo)
	{
		visitUserVoMapper.insert(vo);
	}
	//根据用户名查询最近十次登录信息
	public List selectTheLastTenVisitsByUserName(String userName)
	{
		return visitUserVoMapper.selectTheLastTenVisitsByUserName(userName);
	}
	//根据用户名查询所有登录次数
	public int selectAllVisitNumsByUserName(String userName)
	{
		return visitUserVoMapper.selectAllVisitNumsByUserName(userName);
	}
}
