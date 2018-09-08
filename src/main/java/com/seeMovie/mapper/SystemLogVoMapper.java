package com.seeMovie.mapper;

import com.seeMovie.pojo.MenuVo;
import com.seeMovie.pojo.SystemLogVo;
import java.util.List;
import java.util.Map;

public interface SystemLogVoMapper {
    int insert(SystemLogVo systemLogVo);

    List<SystemLogVo> selectAll();
    //多条件查询系统日志总数
    int selectAllSystemLogCountInfoByParam(Map<String, Object> returnMap);
    //多条件查询系统日志总数 列表明细
    List<MenuVo> selectAllSystemLogInfoByParam(Map<String, Object> returnMap);
}