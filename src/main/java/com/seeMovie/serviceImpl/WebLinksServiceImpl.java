package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.WebLinksVoMapper;
import com.seeMovie.pojo.WebLinksVo;
import com.seeMovie.service.WebLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WebLinksServiceImpl implements WebLinksService {
	@Autowired
	WebLinksVoMapper webLinksVoMapper;
	public WebLinksServiceImpl() {
	}

	@Override
	public Map<String, Object> selectAllWebLinksByParam(Map<String, Object> returnMap) {
		try{
			//查询符合条件数据集合
			List<WebLinksVo> rows = webLinksVoMapper.selectAllWebLinksByParam(returnMap);
			//查询符合条件数据总数
			int total = webLinksVoMapper.selectAllWebLinksCountByParam(returnMap);
			returnMap.put("total",total);
			returnMap.put("rows",rows);
		}catch(Exception e){
			returnMap.put("total",0);
			returnMap.put("rows",null);
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public void insertWebLinkVo(WebLinksVo webLinksVo) {
		try{
			webLinksVoMapper.insert(webLinksVo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//根据主键id编辑待爬取网站信息
	@Override
	public void updateWebLinkVo(WebLinksVo webLinksVo) {
		try{
			webLinksVoMapper.updateWebLinkVo(webLinksVo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
