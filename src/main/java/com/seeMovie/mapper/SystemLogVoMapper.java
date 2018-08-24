package com.seeMovie.mapper;

import com.seeMovie.pojo.SystemLogVo;
import java.util.List;

public interface SystemLogVoMapper {
    int insert(SystemLogVo systemLogVo);

    List<SystemLogVo> selectAll();
}