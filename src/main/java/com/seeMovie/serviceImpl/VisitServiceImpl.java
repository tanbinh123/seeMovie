package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.VisitUserInfoVoMapper;
import com.seeMovie.pojo.VisitUserInfoVo;
import com.seeMovie.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {
	@Autowired
	VisitUserInfoVoMapper VisitUserInfoVoMapper;
	public VisitServiceImpl() {
	}
	//根据条件查询对应访问者的ip信息
	@Override
	public Map<String, Object> selectAllVisitInfoByParam(Map<String, Object> returnMap) {
		try{
			int total = VisitUserInfoVoMapper.selectAllVisitInfoCountByParam(returnMap);
			List<VisitUserInfoVo> rows = VisitUserInfoVoMapper.selectAllVisitInfoByParam(returnMap);
			returnMap.put("total",total);
			returnMap.put("rows",rows);
		}catch(Exception e){
			returnMap.put("total",0);
			returnMap.put("rows",null);
			e.printStackTrace();
		}
		return returnMap;
	}
}
