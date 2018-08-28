package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.MenuVoMapper;
import com.seeMovie.mapper.VisitUserInfoVoMapper;
import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.MenuVo;
import com.seeMovie.pojo.VisitUserInfoVo;
import com.seeMovie.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	@Autowired
	VisitUserInfoVoMapper VisitUserInfoVoMapper;
	@Autowired
	MenuVoMapper menuVoMapper;

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
	//查询系统当日首页访问量   访问ip去重总数
	@Override
	public int selectTotalVisitOfToday() {
		return VisitUserInfoVoMapper.selectTotalVisitOfToday();
	}
	//查找所有菜单数据集合
	@Override
	public List<MenuQueryVo> selectAllMenuList() {
		//根据条件查询对应菜单
		List<MenuQueryVo> returnMenuQueryVoList = menuVoMapper.selectAllParentMenu("0");//先查父级菜单
		for (MenuQueryVo menuQueryVo : returnMenuQueryVoList) {
			List<MenuQueryVo> returnMenuVoList = menuVoMapper.selectAllParentMenu(menuQueryVo.getMenuId());//根据父级菜单查询子菜单
			menuQueryVo.setMenuQueryVoList(returnMenuVoList);
		}
		return returnMenuQueryVoList;
	}
}
