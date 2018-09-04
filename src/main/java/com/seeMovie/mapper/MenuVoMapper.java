package com.seeMovie.mapper;

import com.seeMovie.pojo.MenuQueryVo;
import com.seeMovie.pojo.MenuVo;
import java.util.List;
import java.util.Map;

public interface MenuVoMapper {
    int insert(MenuVo menuVo);

    List<MenuVo> selectAll();
    //查询符合条件的菜单总数
    int selectCountMenuListByParam(Map<String, Object> returnMap);
    //查询符合条件的菜单数据
    List<MenuVo> selectMenuListByParam(Map<String, Object> returnMap);
    //根据条件查询对应菜单
    List<MenuQueryVo> selectAllMenuByParam(Object o);
    //查询所有的父级菜单
    List<MenuVo> selectAllParentMenu();
}