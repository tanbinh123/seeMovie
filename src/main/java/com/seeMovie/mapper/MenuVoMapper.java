package com.seeMovie.mapper;

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
}