package com.seeMovie.serviceImpl;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seeMovie.mapper.MovieMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.mapper.MovieCategoryVoMapper;
import com.seeMovie.pojo.MovieCategoryVo;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.pojo.WebLinksVo;
import com.seeMovie.service.MovieService;
@Service
@Transactional
public class MovieServiceImpl implements MovieService{
	//默认图片链接
	private static final String default_img_url = "https://img1.doubanio.com/dae/niffler/niffler/images/ba356172-7825-11e8-9fa1-0242ac110017.png";
	@Autowired
	MovieMapper movieMapper;
	@Autowired
	MovieCategoryVoMapper movieCategoryVoMapper;
	@Override
	public void insertAlldownHrefByList(List<Map<String, Object>> downHrefList,WebLinksVo webLinksVo) {
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
				vo.setSource(webLinksVo.getWebLink());
				vo.setRemarks("定时器获取数据！");
				vo.setSynchronousFlag("N");
				vo.setSynchronousImgUrlFlage("N");
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
	private MovieVo getNewVoByParam(List<String> movieHrefAndImgUrl, MovieVo movieVo) {
		movieVo.setCategory("teleplay");
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
			String describe = "";
			for (int i=0;i<describesArr.length;i++) {
				describe = describesArr[i];
/*◎译　　名　复仇者联盟3：无限战争/复仇者联盟3：无限之战(港)/复仇者联盟：无限之战(台)/复仇者联盟3：无尽之战/复联3/妇联3(豆友译名)/复仇者联盟3：灭霸传(豆友译名) 
◎片　　名　Avengers: Infinity War 
◎年　　代　2018 
◎产　　地　美国 
◎类　　别　动作/科幻/奇幻/冒险 
◎语　　言　英语 
◎字　　幕　中英双字幕 
◎上映日期　2018-04-23(加州首映)/2018-04-27(美国)/2018-05-11(中国) 
◎IMDb评分 8.7/10 from 407,821 users 
◎豆瓣评分　8.2/10 from 338,916 users 
◎文件格式　rmvb + aac 
◎视频尺寸　1280 x 720 
◎文件大小　1CD 
◎片　　长　150分钟 
◎导　　演　安东尼·卢素 Anthony Russo / 乔·卢素 Joe Russo 
◎主　　演　小罗伯特·唐尼 Robert Downey Jr. 
　　　　　　克里斯·海姆斯沃斯 Chris Hemsworth 

◎简　　介 

《复仇者联盟3：无限战争》是漫威电影宇宙10周年的历史性集结，将为影迷们带来史诗版的终极对决。面对灭霸突然发起的闪电袭击，复仇者联盟及其所有超级英雄盟友必须全力以赴，才能阻止他对全宇宙造成毁灭性的打击。  
				 */ 

				if(!StringUtils.isEmpty(describe) && describe.trim().contains("类　　别")){
					if(describe.contains("喜剧")||describe.contains("爱情")){
						movieVo.setCategory("category1");
						movieVo.setSynchronousFlag("Y");
					}else if(describe.contains("剧情")||describe.contains("文艺")){
						movieVo.setCategory("category2");
						movieVo.setSynchronousFlag("Y");
					}else if(describe.contains("动作")||describe.contains("战争")){
						movieVo.setCategory("category3");
						movieVo.setSynchronousFlag("Y");
					}else if(describe.contains("科幻")||describe.contains("冒险")){
						movieVo.setCategory("category4");
						movieVo.setSynchronousFlag("Y");
					}else if(describe.contains("犯罪")||describe.contains("悬疑")){
						movieVo.setCategory("category5");
						movieVo.setSynchronousFlag("Y");
					}else if(describe.contains("恐怖")||describe.contains("惊悚")){
						movieVo.setCategory("category6");
						movieVo.setSynchronousFlag("Y");
					}else{
						movieVo.setCategory("category");//其他类型
						movieVo.setSynchronousFlag("Y");
					}
				}

				/*if(!StringUtils.isEmpty(describe) && describe.trim().contains("片　　名")){//◎片　　名　Avengers: Infinity War 
					//其实影片名称前面已经截取  但是那是通过连接截取的名称  没有直接从影片简介上截取的准确
					describe = describe.replaceAll("　　","").replaceAll("　", "");
					movieVo.setMovieName(describe.trim().length()>2?describe.trim().substring(2,describe.trim().length()).trim():"");
				}*/
				 
				if(!StringUtils.isEmpty(describe) && describe.trim().contains("年　　代")){//◎年　　代　2018 
					describe = describe.replaceAll("　　","").replaceAll("　", "");
					movieVo.setProduceYear(describe.trim().length()>3?describe.trim().substring(3,describe.trim().length()).trim():"");
				}

				if(!StringUtils.isEmpty(describe) && describe.trim().contains("产　　地")){//◎产　　地　美国 
					describe = describe.replaceAll("　　","").replaceAll("　", "");
					movieVo.setProduceCountry(describe.trim().length()>3?describe.trim().substring(3,describe.trim().length()).trim():"");
				}

				if(!StringUtils.isEmpty(describe) && describe.trim().contains("豆瓣评分")){//◎豆瓣评分　8.2/10 from 338,916 users 
					describe = describe.replaceAll("　","");
					describe = describe.trim().length()>8?describe.trim().substring(5,8).trim():"";//豆瓣评分8.2
					movieVo.setDoubanScore(new BigDecimal(describe));
				}

				if(!StringUtils.isEmpty(describe) && describe.trim().contains("简　　介")){
					if(i+1 < describesArr.length &&  !describesArr[i+1].isEmpty()){//简介换行多次后才是具体的描述值
						if(describesArr[i+1].length()>2500){
							movieVo.setDescribes(describesArr[i+1].trim().substring(0,2500));
							break;
						}else{
							movieVo.setDescribes(describesArr[i+1].trim());
							break;
						}
					}else if(i+2 < describesArr.length &&  !describesArr[i+2].isEmpty()){
						if(describesArr[i+2].length()>2500){
							movieVo.setDescribes(describesArr[i+2].trim().substring(0,2500));
							break;
						}else{
							movieVo.setDescribes(describesArr[i+2].trim());
							break;
						}
					}else if(i+3 < describesArr.length &&  !describesArr[i+3].isEmpty()){
						if(describesArr[i+3].length()>2500){
							movieVo.setDescribes(describesArr[i+3].trim().substring(0,2500));
							break;
						}else{
							movieVo.setDescribes(describesArr[i+3].trim());
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
			if(imgUrl.indexOf(".jpg")+4 < imgUrl.length()){//说明存在多个.jpg结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".jpg")+4);
			}
		}else if(imgUrl.contains(".JPG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("JPG")+3);
			if(imgUrl.indexOf(".JPG")+4 < imgUrl.length()){//说明存在多个.JPG结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".JPG")+4);
			}
		}else if(imgUrl.contains(".jpeg")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("jpeg")+3);
			if(imgUrl.indexOf(".jpeg")+5 < imgUrl.length()){//说明存在多个.jpeg结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".jpeg")+5);
			}
		}else if(imgUrl.contains(".JPEG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("JPEG")+3);
			if(imgUrl.indexOf(".JPEG")+5 < imgUrl.length()){//说明存在多个.JPEG结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".JPEG")+5);
			}
		}else if(imgUrl.contains(".png")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("png")+3);
			if(imgUrl.indexOf(".png")+4 < imgUrl.length()){//说明存在多个.png结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".png")+4);
			}
		}else if(imgUrl.contains(".PNG")){
			imgUrl =  imgUrl.substring(imgUrl.indexOf(htppType),imgUrl.lastIndexOf("PNG")+3);
			if(imgUrl.indexOf(".PNG")+4 < imgUrl.length()){//说明存在多个.PNG结尾
				imgUrl = imgUrl.substring(0,imgUrl.indexOf(".PNG")+4);
			}
		}else{
			imgUrl = default_img_url;
		}
		if(imgUrl.length()>150){//图片链接过长，一般是非法链接，不可访问，附上默认的访问链接
			imgUrl = default_img_url;
		}
		return imgUrl;
	}
	//根据电影id查看电影详情
	@Override
	public MovieVo getMovieDetail(String movieId) {
		return movieMapper.getMovieDetail(movieId);
	}
	//定时更新影片信息  例如影片类型、影片分类等
	@Override
	public void UpdateMovieCategoryInfoTimer() {
		Map<String,Object> paramMap = new HashMap<>();
		int index = 0;//这个是名称中第一个为汉字的下标
		try {
			paramMap.put("category","teleplay");//影片接进来时默认值
			paramMap.put("synchronousFlag","N");//还没有处理的数据
			paramMap.put("synchronousFlagType","synchronousFlagType");//过滤标识
			List<MovieVo> movieList = movieMapper.getAllMovie(paramMap);
			String checkName = "";
			int movieNameNum = 0;
			List<String> movieIdList = new ArrayList<>();//用来封装每一个跟上一个电影名字不同的电影id   其实装的就是电视剧数据 
			List<String> allMovieIdList = new ArrayList<>();//最终要更新影片类型的影片id集合  更新synchronousFlag为Y
			List<String> allUpdateMovieId = new ArrayList<>();//最终要更新影片类型的影片id集合      其实就是电视剧数据
			for (int i = 0; i < movieList.size(); i++) {
				String movieName = movieList.get(i).getMovieName().trim().substring(0);
				if(!StringUtils.isEmpty(movieName) && movieName.length() >=2 && isChinese(movieName.substring(0, 1))){//汉字开头
					movieName = movieName.substring(0, 2);//截取2位作为是否名字有重复的标识	
				}else{//非汉字开头
					if(movieName.length()>=8){
						//2166/神犬小七2HDTV19.mp4  2121/神犬小七2HDTV18.mp4 类似于这种名字
						//截取相同的部分
						//movieName = movieName.substring(movieName.length()/2,movieName.length()/2+movieName.length()/4);
						//movieName = movieName.substring(4,7);//截取3位作为判断标准
						index = getChineseIndex(movieName);
						//截取2位作为判断标准
						movieName = movieName.substring(index,index+2 <= movieName.length()?index+2:index+1);
						index = 0;
					}
				}
				if(i == 0){
					checkName = movieName;
					movieIdList.add(movieList.get(i).getMovieId());
				}else{
					if(movieName.equals(checkName)){//重复影片
						movieNameNum += 1;
						movieIdList.add(movieList.get(i).getMovieId());
						if(i == movieList.size() -1){//此处加这个逻辑主要是解决可能存在当前待更新的数据都是同一个电视剧而不更新的bug
							allUpdateMovieId.addAll(movieIdList);
						}
					}else{//新影片
						if(movieNameNum >= 2){//名字重复出现3次及以上才认为是电视剧
							allUpdateMovieId.addAll(movieIdList);
							checkName = movieName;
							movieNameNum = 0;
							movieIdList.clear();
							movieIdList.add(movieList.get(i).getMovieId());
						}else{
							allMovieIdList.addAll(movieIdList);//电影  更新synchronousFlag为Y并更新更新时间字段
							checkName = movieName;
							movieNameNum = 0;
							movieIdList.clear();
							movieIdList.add(movieList.get(i).getMovieId());
						}
					}
				}
			}
			if(!allUpdateMovieId.isEmpty()){//更新影片类型为teleplay电视剧，同步标志为Y,影片同步日期
				paramMap.put("type", "teleplay");
				paramMap.put("allUpdateMovieIdList",allUpdateMovieId);
				movieMapper.updateMovieInfoByMovieIdList(paramMap);
			}
			if(!allMovieIdList.isEmpty()){//更新同步标志为Y,影片同步日期   电影
				paramMap.put("type", "movie");
				paramMap.put("allUpdateMovieIdList",allMovieIdList);
				movieMapper.updateMovieInfoByMovieIdList(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//通过名称拿到第一个汉字的index
	private int getChineseIndex(String movieName) {
		for(int i=0;i<movieName.length();i++){
			if(isChinese(movieName.substring(i,i+1))){
				return i;
			}
		}
		return 0;
	}
	@Override
	public Map<String, Object> selectMovieInfoByParam(PagingUtil pagingUtil, Map<String, Object> map) {
		try {
			//最终返回的数据集合
			List<List<MovieVo>> returnList = new ArrayList<>();
			//封装查询参数
			map.put("currentPage", pagingUtil.getCurrentPage());
			map.put("pageSize", pagingUtil.getPageSize());
			//查找影片集合
			List<MovieVo> movieVoList =  movieMapper.selectAllMovieVo(map);
			//查找影片分类集合
			List<MovieCategoryVo> movieCategoryList =  movieCategoryVoMapper.selectAll();
			if(map.get("showType")!=null && map.get("showType").toString().trim().equals("picture")){
				int rowNum = (int) map.get("rowNum");//4 6 8 10
				if(movieVoList != null && movieVoList.size() > 0){//每四个分为一组
					for(int i = 0; i<movieVoList.size();i++){
						if((i+1)%rowNum == 0){//例如下标位 3、7、11。。。。。。的元素   (i+1)%4
							List<MovieVo> newMovieList = new ArrayList<>();
							for(int j=i+1-rowNum;j<=i;j++){//j=i-3
								newMovieList.add(movieVoList.get(j));
							}
							returnList.add(newMovieList);
						}else if(i>=(movieVoList.size()/rowNum*rowNum)){//假设:movieVoList.size()为18,则/4  得到4,4*4=16
							List<MovieVo> newMovieList = new ArrayList<>();
							for(int j=i;j<movieVoList.size();j++){
								newMovieList.add(movieVoList.get(j));
							}
							returnList.add(newMovieList);
							break;
						}
					}
				}
				map.put("movieList", returnList);
			}else{
				map.put("movieList", movieVoList);
			}
			int total = movieMapper.selectMovieVoCount(map);
			pagingUtil.setTotal(total);
			if(total%pagingUtil.getPageSize() == 0){
				pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize());
			}else{
				pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize()+1);
			}
			map.put("movieCategoryList", movieCategoryList);
			map.put("pagingUtil", pagingUtil);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	//定时更新影片的图片链接信息 无法访问的图片链接用默认图片代替
	@Override
	public void UpdateMovieImgUrlInfoTimer() {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("synchronousImgUrlFlage","N");//影片图片链接同步标志(Y/N)   接进来时为N
			boolean status = false;
			List<MovieVo> movieList = movieMapper.getAllMovie(paramMap);
			for (MovieVo movieVo : movieList) {
				movieVo.setSynchronousImgUrlFlage("Y");//更新图片检查标识为Y
				status = checkImgUrlCanUseOrNo(movieVo.getImgUrl());
				if(status){//图片1可访问
					status = checkImgUrlCanUseOrNo(movieVo.getImgUrl2());
					if(!status){//图片2不可访问
						movieVo.setImgUrl2(movieVo.getImgUrl());
					}
				}else{//图片1不可访问
					movieVo.setImgUrl(default_img_url);
					status = checkImgUrlCanUseOrNo(movieVo.getImgUrl2());
					if(!status){//图片2不可访问
						movieVo.setImgUrl2(default_img_url);
					}else{
						movieVo.setImgUrl(movieVo.getImgUrl2());
					}
				}
			}
			if(!movieList.isEmpty()){
				movieMapper.updateMovieByList(movieList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author      马玉谋
	 * @date        2018年7月26日下午7:27:43
	 * @describe    校验链接是否可用
	 * @return      boolean
	 */
	private boolean checkImgUrlCanUseOrNo(String url) {
		try {
			if(StringUtils.isEmpty(url) || !url.startsWith("http")){
				return false;
			}
			// 设置此类是否应该自动执行 HTTP重定向（响应代码为 3xx 的请求）。
			HttpURLConnection.setFollowRedirects(false);
			// 到URL所引用的远程对象的连接
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			//设置请求超时
			conn.setConnectTimeout(5000);
			// 设置URL请求的方法，GET POST HEAD OPTIONS PUT DELETE TRACE
			// 以上方法之一是合法的，具体取决于协议的限制。
			conn.setRequestMethod("HEAD");
			// 从HTTP响应消息获取状态码
			if(HttpURLConnection.HTTP_OK == 200 && conn.getResponseCode() != 404){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 
	 * @author      马玉谋
	 * @date        2018年7月26日下午7:28:04
	 * @describe    校验传入的是否是汉字
	 * @return      boolean
	 */
	public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()){
			flg = true;
		}
		return flg;
	}
	//每次查询两条网站初始化链接
	@Override
	public List<WebLinksVo> getWebLinksVo() {
		return movieMapper.getWebLinksVo();
	}
	@Override
	public void updateWebLinks(WebLinksVo webLinksVo) {
		//将当前已经爬取过的网站爬取状态改为Y
		movieMapper.updateWebLinks(webLinksVo);
	}
}
