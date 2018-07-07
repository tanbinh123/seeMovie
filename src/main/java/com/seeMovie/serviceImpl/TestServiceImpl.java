package com.seeMovie.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.seeMovie.mapper.TestMapper;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.TestService;
@Service
@Transactional
public class TestServiceImpl implements TestService{
	@Autowired
	TestMapper testMapper;
	@Override
	public List<List<MovieVo>> selectAllMovieVo() {
		List<List<MovieVo>> returnList = new ArrayList<>();
		List<MovieVo> movieVoList =  testMapper.selectAllMovieVo();
		if(movieVoList != null && movieVoList.size() > 0){//每四个分为一组
			for(int i = 0; i<movieVoList.size();i++){
				if((i+1)%4 == 0){//例如下标位 3、7、11。。。。。。的元素
					List<MovieVo> newMovieList = new ArrayList<>();
					for(int j=i-3;j<=i;j++){
						newMovieList.add(movieVoList.get(j));
					}
					returnList.add(newMovieList);
				}else if(i>=(movieVoList.size()/4*4)){//假设:movieVoList.size()为18,则/4  得到4,4*4=16
					List<MovieVo> newMovieList = new ArrayList<>();
					for(int j=i;j<movieVoList.size();j++){
						newMovieList.add(movieVoList.get(j));
						break;
					}
					returnList.add(newMovieList);
				}
			}
		}
		return returnList;
	}
	@Override
	public void insertAllaHrefByList(List<String> aHrefList,String httpHeader) {
		String newaHref = "";
		String movieName = "";
		try {
			MovieVo vo = new MovieVo();
			for (String aHref : aHrefList) {
				vo.setMovieId(UUID.randomUUID().toString().replaceAll("-", ""));
				//<a> <img src="/images/dytt.jpg" width="180" border="0"></a>######<a href="/index.html"></a>
				newaHref = aHref.substring(0,aHref.length()-1);
				//aHref.substring(0,aHref.length()-1)   拿到<a> <img src="/images/dytt.jpg" width="180" border="0"></a######<a href="/index.html"></a
				if(aHref.contains("http")||aHref.contains("https")){
					vo.setaHref(aHref.substring(aHref.indexOf("=")+2,newaHref.lastIndexOf(">")-1));
				}else{
					vo.setaHref(httpHeader+aHref.substring(aHref.indexOf("=")+2,newaHref.lastIndexOf(">")-1));
				}
				
				if(aHref.substring(newaHref.lastIndexOf(">")+1,aHref.length()).equals("</a>")){
					//该判断是避免没有名字时截取时导致下表越界  例如//  <a href="/index.html"></a>
					vo.setMovieName("未知资源");
				}else{
					movieName= aHref.substring(newaHref.lastIndexOf(">")+1,newaHref.lastIndexOf("<"));
					if(movieName.length()>200){//超过两百  名字只保留200位
						vo.setMovieName(movieName.substring(0,200));
					}else{
						vo.setMovieName(movieName);
					}
					
				}
				if(vo.getaHref().contains("html") && ((vo.getaHref().lastIndexOf("html")+4)<vo.getaHref().length())){
					//链接如： .html" 等等......(不合法链接)   需要截掉html后面多余字符
					//System.out.println("前"+vo.getaHref());
					vo.setaHref(vo.getaHref().substring(0,vo.getaHref().lastIndexOf("html")+4));
					//System.out.println("后"+vo.getaHref());
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
