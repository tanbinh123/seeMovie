package com.seeMovie.mapper;

import com.seeMovie.pojo.WebLinksVo;
import java.util.List;

public interface WebLinksVoMapper {
    int insert(WebLinksVo record);

    List<WebLinksVo> selectAll();
}