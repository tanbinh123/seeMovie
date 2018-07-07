package com.seeMovie.mapper;

import com.seeMovie.pojo.AHrefVo;
public interface TestMapper {

	String getAll();
	//保存当前电影资源
	void insertaHrefByVo(AHrefVo vo);
	//根据保存的截取链接判断当前数据是否存在  存在则不保存
	int selectaHrefVoByHref(String getaHref);

}
