package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.VisitUserInfoVoMapper;
import com.seeMovie.pojo.VisitUserInfoVo;
import com.seeMovie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	@Autowired
	VisitUserInfoVoMapper VisitUserInfoVoMapper;

	public LoginServiceImpl() {
	}

	public void insertVisitUserInfoVo(VisitUserInfoVo vo) {
		VisitUserInfoVoMapper.insert(vo);
	}

	// 根据用户名查询最近十次登录信息
	public List<VisitUserInfoVo> selectTheLastTenVisitsByUserName(String userName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<VisitUserInfoVo>  returnList = VisitUserInfoVoMapper.selectTheLastTenVisitsByUserName(userName);
		for (VisitUserInfoVo visitUserInfoVo : returnList) {
			if(visitUserInfoVo.getVisitDate() != null){
				visitUserInfoVo.setVisitDateStr(sdf.format(visitUserInfoVo.getVisitDate()));
			}
		}
		return returnList;
	}

	// 根据用户名查询所有登录次数
	public int selectAllVisitNumsByUserName(String userName) {
		return VisitUserInfoVoMapper.selectAllVisitNumsByUserName(userName);
	}
}
