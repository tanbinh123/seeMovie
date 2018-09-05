package com.seeMovie.mapper;

import com.seeMovie.pojo.WebLinksVo;
import java.util.List;
import java.util.Map;

public interface WebLinksVoMapper {
    int insert(WebLinksVo record);

    List<WebLinksVo> selectAll();
    //查询符合条件数据集合
    List<WebLinksVo> selectAllWebLinksByParam(Map<String, Object> returnMap);
    //查询符合条件数据总数
    int selectAllWebLinksCountByParam(Map<String, Object> returnMap);
    //根据主键id编辑待爬取网站信息
    void updateWebLinkVo(WebLinksVo webLinksVo);
}