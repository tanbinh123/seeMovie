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

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.mapper.MovieMapper;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.MovieService;
@Service
@Transactional
public class MovieServiceImpl implements MovieService{
	//默认图片链接
	private static final String default_img_url = "https://img1.doubanio.com/dae/niffler/niffler/images/ba356172-7825-11e8-9fa1-0242ac110017.png";
	@Autowired
	MovieMapper movieMapper;
	@Override
	public List<List<MovieVo>> selectAllMovieVo(PagingUtil pagingUtil) {
		//最终返回的数据集合
		List<List<MovieVo>> returnList = new ArrayList<>();
		//封装参数
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("currentPage", pagingUtil.getCurrentPage());
		paramMap.put("pageSize", pagingUtil.getPageSize());
		List<MovieVo> movieVoList =  movieMapper.selectAllMovieVo(paramMap);
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
	public void insertAlldownHrefByList(List<Map<String, Object>> downHrefList, String webLinks) {
		try {
			MovieVo vo = new MovieVo();
			for (Map<String, Object> map : downHrefList) {
				String downHref = map.get("downHrf").toString();
				//String movieName = map.get("movieName").toString();
				String describes = map.get("describes").toString();
				//<a href="ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭人生.HD.1080p.国语中字.mp4">ftp://ygdy8:ygdy8@yg90.dydytt.net:8356/阳光电影www.ygdy8.com.逆袭
				//人生.HD.1080p.国语中字.mp4</a>#<img src="/images/bbs_btn.gif" alt="电影天堂" border="0">#<img border="0" src="http://www.imageto.org/images/3vrHn.jpg" alt="">#<img 
				//border="0" src="http://www.imageto.org/images/nos3.jpg" alt="">
				vo.setMovieId(UUID.randomUUID().toString().replaceAll("-", ""));
				List<String> movieHrefAndImgUrl = getMovieHrefAndImgUrl(downHref);
				vo.setDescribes(describes);
				vo = getNewVoByParam(movieHrefAndImgUrl,vo);
				vo.setDescribes(describes);
				vo.setSource(webLinks);
				vo.setRemarks("定时器获取数据！");
				vo.setInsertDate(new Date());
				//根据保存的截取链接判断当前数据是否存在  存在则不保存
				int num= movieMapper.selectDownHrefVoByHref(vo.getDownHref());
				if(num == 0){
					movieMapper.insertDownHrefByVo(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private MovieVo getNewVoByParam(List<String> movieHrefAndImgUrl,MovieVo movieVo) {
		if(movieHrefAndImgUrl!=null && movieHrefAndImgUrl.size()>=2){//至少存在电影名字及下载链接
			//最多保留两张图片链接
			movieVo.setDownHref(movieHrefAndImgUrl.get(0));
			movieVo.setMovieName(movieHrefAndImgUrl.get(1));
			//初始化赋值
			movieVo.setImgUrl(default_img_url);
			if(movieHrefAndImgUrl.size() == 3){
				movieVo.setImgUrl(movieHrefAndImgUrl.get(2));
			}else if(movieHrefAndImgUrl.size() >= 4){
				if(movieHrefAndImgUrl.get(2).equals(default_img_url)){
					//图片链接1位默认值 则将图片2的链接赋值给图片1 
					//因为会出现图片1截取链接时无值用默认值代替，但是图片2有链接的情况
					movieVo.setImgUrl(movieHrefAndImgUrl.get(3));
					movieVo.setImgUrl2(movieHrefAndImgUrl.get(3));
				}else{
					movieVo.setImgUrl(movieHrefAndImgUrl.get(2));
					movieVo.setImgUrl2(movieHrefAndImgUrl.get(3));
				}
			}
		}
		if(!movieVo.getDescribes().equals("暂无当前影片详情！")){
			//String describes = movieVo.getDescribes();
			String[] describesArr = movieVo.getDescribes().split("<br>");
			for (String describe : describesArr) {
				/*◎译　　名　西伯利亚 
				◎片　　名　Siberia 
				◎年　　代　2018 
				◎产　　地　美国 
				◎类　　别　爱情/犯罪/惊悚 
				◎语　　言　英语 
				◎字　　幕　中英双字幕 
				◎上映日期　2018-07-13(美国) 
				◎IMDb评分 6.9/10 from 69 users 
				◎文件格式　x264 + aac 
				◎视频尺寸　1280 x 720 
				◎文件大小　1CD 
				◎导　　演　Matthew M. Ross 
				◎主　　演　基努·里维斯 Keanu Reeves 
				　　　　　　莫利·林沃德 Molly Ringwald 
				　　　　　　安娜·乌拉鲁 Ana Ularu 
				　　　　　　阿莱克斯·潘诺维奇 Aleks Paunovic 
				　　　　　　帕沙·D.林奇尼科夫 Pasha D. Lychnikoff 
				　　　　　　维罗尼卡·费瑞尔 Veronica Ferres 
				　　　　　　尤金·里皮斯基 Eugene Lipinski 
				　　　　　　詹姆斯·亚历山大 James Alexander 
				　　　　　　布拉德利·索茨基 Bradley Sawatzky 
				　　　　　　纳扎利·德姆克维奇 Nazariy Demkowicz 
				　　　　　　维塔利·马卡罗夫 Vitali Makarov 
				　　　　　　达伦·罗斯 Darren Ross
				
				◎简　　介　 
				*/ 
				if(!StringUtils.isEmpty(describe) && describe.contains("简　　介")){
					String[] newDescribeArr = describe.split("<br>");
					if(newDescribeArr.length>2){//超过两组  还需要在次循环取值
						for (String newDescribe : newDescribeArr) {
							if(!StringUtils.isEmpty(newDescribe) && newDescribe.contains("简　　介")){
								if(newDescribe.length()>2500){
									movieVo.setDescribes(newDescribe.substring(0,2500));
									break;
								}else{
									movieVo.setDescribes(newDescribe);
									break;
								}
							}
						}
					}else{
						if(describe.length()>2500){
							movieVo.setDescribes(describe.substring(0,2500));
							break;
						}else{
							movieVo.setDescribes(describe);
							break;
						}
					}
				}
			}
		}
		return movieVo;
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
		return movieMapper.selectMovieVoCount();
	}
	//根据电影id查看电影详情
	@Override
	public MovieVo getMovieDetail(String movieId) {
		return movieMapper.getMovieDetail(movieId);
	}
}
