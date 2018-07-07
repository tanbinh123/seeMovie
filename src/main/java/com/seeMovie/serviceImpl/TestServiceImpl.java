package com.seeMovie.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.seeMovie.mapper.TestMapper;
import com.seeMovie.pojo.AHrefVo;
import com.seeMovie.service.TestService;
@Service
@Transactional
public class TestServiceImpl implements TestService{
	@Autowired
	TestMapper testMapper;
	@Override
	public String getAll() {
		return testMapper.getAll();
	}
	@Override
	public void insertAllaHrefByList(List<String> aHrefList) {
		String newaHref = "";
		try {
			AHrefVo vo = new AHrefVo();
			for (String aHref : aHrefList) {
				vo.setMovieId(UUID.randomUUID().toString().replaceAll("-", ""));
				//<a> <img src="/images/dytt.jpg" width="180" border="0"></a>######<a href="/index.html"></a>
				newaHref = aHref.substring(0,aHref.length()-1);
				//aHref.substring(0,aHref.length()-1)   拿到<a> <img src="/images/dytt.jpg" width="180" border="0"></a######<a href="/index.html"></a
				vo.setaHref(aHref.substring(aHref.indexOf("=")+2,newaHref.lastIndexOf(">")-1));
				if(aHref.substring(newaHref.lastIndexOf(">")+1,aHref.length()).equals("</a>")){
					//该判断是避免没有名字时截取时导致下表越界  例如//  <a href="/index.html"></a>
					vo.setMovieName("未知资源");
				}else{
					vo.setMovieName(aHref.substring(newaHref.lastIndexOf(">")+1,newaHref.lastIndexOf("<")));
				}
				vo.setInsertDate(new Date());
				//根据保存的截取链接判断当前数据是否存在  存在则不保存
				int num= testMapper.selectaHrefVoByHref(vo.getaHref());
				if(num == 0){
					testMapper.insertaHrefByVo(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
