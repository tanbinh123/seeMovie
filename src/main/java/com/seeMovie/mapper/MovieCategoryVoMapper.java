package com.seeMovie.mapper;

import com.seeMovie.pojo.MovieCategoryVo;
import java.util.List;

public interface MovieCategoryVoMapper {
    int insert(MovieCategoryVo record);

    List<MovieCategoryVo> selectAll();

}