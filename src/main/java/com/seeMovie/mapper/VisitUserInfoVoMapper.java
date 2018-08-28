package com.seeMovie.mapper;

import com.seeMovie.pojo.VisitUserInfoVo;
import java.util.List;

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
}