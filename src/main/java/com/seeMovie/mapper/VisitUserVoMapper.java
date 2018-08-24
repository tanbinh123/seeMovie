package com.seeMovie.mapper;

import com.seeMovie.pojo.VisitUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitUserVoMapper {
    int insert(VisitUserVo record);

    List<VisitUserVo> selectAll();
    //根据用户名查询最近十次登录信息
    List selectTheLastTenVisitsByUserName(@Param("userName") String userName);
    //根据用户名查询所有登录次数
    int selectAllVisitNumsByUserName(@Param("userName")String userName);
}