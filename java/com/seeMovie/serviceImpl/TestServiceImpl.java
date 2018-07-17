package com.seeMovie.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.mapper.TestMapper;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.TestService;
@Service
@Transactional
public class TestServiceImpl implements TestService{
	//默认图片链接
	private static final String default_img_url = "https://img1.doubanio.com/dae/niffler/niffler/images/ba356172-7825-11e8-9fa1-0242ac110017.png";
	@Autowired
	TestMapper testMapper;
	@Override
	public List<List<MovieVo>> selectAllMovieVo(PagingUtil pagingUtil) {
		//最终返回的数据集合
		List<List<MovieVo>> returnList = new ArrayList<>();
		//封装参数
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("currentPage", pagingUtil.getCurrentPage());
		paramMap.put("pageSize", pagingUtil.getPageSize());
		List<MovieVo> movieVoList =  testMapper.selectAllMovieVo(paramMap);
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
	public void insertAlldownHrefByList(List<String> downHrefList,String webLinks) {
		try {
			MovieVo vo = new MovieVo();
			for (String downHref : downHrefList) {
				//<a href="ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭人生.HD.1080p.国语中字.mp4">ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭
				//人生.HD.1080p.国语中字.mp4</a>#<img src="/images/bbs_btn.gif" alt="电影天堂" border="0">#<img border="0" src="http://www.imageto.org/images/3vrHn.jpg" alt="">#<img 
				//border="0" src="http://www.imageto.org/images/nos3.jpg" alt="">
				vo.setMovieId(UUID.randomUUID().toString().replaceAll("-", ""));
				List<String> movieHrefAndImgUrl = getMovieHrefAndImgUrl(downHref);
				if(movieHrefAndImgUrl!=null && movieHrefAndImgUrl.size()>=2){//至少存在电影名字及下载链接
					//最多保留两张图片链接
					vo.setDownHref(movieHrefAndImgUrl.get(0));
					vo.setMovieName(movieHrefAndImgUrl.get(1));
					//初始化赋值
					vo.setImgUrl(default_img_url);
					if(movieHrefAndImgUrl.size() == 3){
						vo.setImgUrl(movieHrefAndImgUrl.get(2));
					}else if(movieHrefAndImgUrl.size() >= 4){
						if(movieHrefAndImgUrl.get(2).equals(default_img_url)){
							//图片链接1位默认值 则将图片2的链接赋值给图片1 
							//因为会出现图片1截取链接时无值用默认值代替，但是图片2有链接的情况
							vo.setImgUrl(movieHrefAndImgUrl.get(3));
							vo.setImgUrl2(movieHrefAndImgUrl.get(3));
						}else{
							vo.setImgUrl(movieHrefAndImgUrl.get(2));
							vo.setImgUrl2(movieHrefAndImgUrl.get(3));
						}
					}
				}
				vo.setSource(webLinks);
				vo.setRemarks("定时器获取数据！");
				vo.setInsertDate(new Date());
				//根据保存的截取链接判断当前数据是否存在  存在则不保存
				int num= testMapper.selectDownHrefVoByHref(vo.getDownHref());
				if(num == 0){
					testMapper.insertDownHrefByVo(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//将定时器传过来的链接截取出电影下载链接、电影名称及图片链接
	private List<String> getMovieHrefAndImgUrl(String downHref) {
		List<String> retuenList = new ArrayList<>();
		String[] arr = downHref.split("#");
		for(int i=0;i<arr.length;i++){
			if(i == 0){//电影资源下载链接
				String movieHref = arr[0];
				//<a href="ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭人生.HD.1080p.国语中字.mp4">ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭
				//人生.HD.1080p.国语中字.mp4</a>
				movieHref = movieHref.substring(movieHref.lastIndexOf("ftp://"),movieHref.lastIndexOf("</a>"));
				retuenList.add(movieHref);//电影链接
				//电影名称
				movieHref = movieHref.substring(movieHref.lastIndexOf("com")+4,movieHref.length());
				if(movieHref.indexOf(".")==0){//首位包含.
					retuenList.add(movieHref.substring(1,movieHref.length()));
				}else{
					retuenList.add(movieHref);
				}
				
			}else{//图片链接
				retuenList.add(getImgUrl(arr[i]));
			}
		}
		return retuenList;
	}
	//截取图片链接
	private String getImgUrl(String imgUrl) {
		//JPG、JPEG、PNG   合法后缀图片
		//<img border="0" src="http://www.imageto.org/images/3vrHn.jpg" alt="">
		String htppType = "http";
		if(imgUrl.contains("https")){
			htppType = "https";
		}
		if(imgUrl.contains(".jpg")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("jpg")+3);
		}else if(imgUrl.contains(".JPG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("JPG")+3);
		}else if(imgUrl.contains(".jpeg")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("jpeg")+3);
		}else if(imgUrl.contains(".JPEG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("JPEG")+3);
		}else if(imgUrl.contains(".png")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("png")+3);
		}else if(imgUrl.contains(".PNG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("PNG")+3);
		}else{
			imgUrl = default_img_url;
		}
		if(imgUrl.length()>150){//图片链接过长，一般是非法链接，不可访问，附上默认的访问链接
			imgUrl = default_img_url;
		}
		return imgUrl;
	}
	//查找所有总数
	@Override
	public int selectMovieVoCount() {
		return testMapper.selectMovieVoCount();
	}
}
