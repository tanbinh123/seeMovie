package com.seeMovie.mapper;

import com.seeMovie.pojo.VisitUserInfoVo;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface VisitUserInfoVoMapper {
    int insert(VisitUserInfoVo record);

    List<VisitUserInfoVo> selectAll();
    //根据用户名查询最近十次登录信息
    List<VisitUserInfoVo> selectTheLastTenVisitsByUserName(@Param("userName") String userName);
    //根据用户名查询所有登录次数
    int selectAllVisitNumsByUserName(@Param("userName")String userName);
    //查询系统当日首页访问量   访问ip去重总数
    int selectTotalVisitOfToday();
    //多条件查询访问者总条数
    int selectAllVisitInfoCountByParam(Map<String, Object> returnMap);
    //多条件查询访问者详细信息
    List<VisitUserInfoVo> selectAllVisitInfoByParam(Map<String, Object> returnMap);
}