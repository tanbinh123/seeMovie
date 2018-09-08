package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.MenuVoMapper;
import com.seeMovie.pojo.MenuVo;
import com.seeMovie.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuVoMapper menuVoMapper;

	@Override
	public Map<String, Object> selectMenuListByParam(Map<String, Object> returnMap) {
		try{
			int total = menuVoMapper.selectCountMenuListByParam(returnMap);
			List<MenuVo> menuVoList = menuVoMapper.selectMenuListByParam(returnMap);
			returnMap.put("total",total);
			returnMap.put("rows",menuVoList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnMap;
	}
	//新增菜单
	@Override
	public void insertMenuVo(MenuVo menuVo) {
		menuVoMapper.insert(menuVo);
	}
	//查询所有的父级菜单
	@Override
	public List<MenuVo> selectAllParentMenu() {
		try{
			return menuVoMapper.selectAllParentMenu();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//根据菜单id删除菜单
	@Override
	public int deleteMenu(String[] ids) {
		int returnType = 0;
		try{
			menuVoMapper.deleteMenu(ids);
		}catch (Exception e){
			returnType =1;
			e.printStackTrace();
		}
		return returnType;
	}
	//根据菜单id更新菜单数据
	@Override
	public void updateMenuVo(MenuVo menuVo) {
		menuVoMapper.updateMenuVo(menuVo);
	}
}
