package com.seeMovie.serviceImpl;

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
				vo.setSource(webLinks);
				vo.setRemarks("定时器获取数据！");
				vo.setCategory(1);//新增时默认类型都为电影，有专门定时器定时根据自己算法更新影片的类型
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
			String describe = "";
			for (int i=0;i<describesArr.length;i++) {
				describe = describesArr[i];
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
	//根据电影id查看电影详情
	@Override
	public MovieVo getMovieDetail(String movieId) {
		return movieMapper.getMovieDetail(movieId);
	}
	//定时更新影片信息  例如影片类型、影片分类等
	@Override
	public void UpdateMovieCategoryInfoTimer() {
		Map<String,Object> paramMap = new HashMap<>();
		try {
			paramMap.put("category","1");//影片接进来时默认值
			paramMap.put("synchronousFlag","N");//还没有处理的数据
			paramMap.put("synchronousFlagType","synchronousFlagType");//过滤标识
			List<MovieVo> movieList = movieMapper.getAllMovie(paramMap);
			String checkName = "";
			int movieNameNum = 0;
			List<String> movieIdList = new ArrayList<>();//用来封装每一个跟上一个电影名字不同的电影id
			List<String> allUpdateMovieId = new ArrayList<>();//最终要更新影片类型的影片id集合
			for (int i = 0; i < movieList.size(); i++) {
				String movieName = "";
				if(isChinese(movieName.substring(0, 1))){//汉字开头
					if(movieList.get(i).getMovieName().length()>=2){
						movieName = movieList.get(i).getMovieName().substring(0, 2);//截取2位作为是否名字有重复的标识	
					}else{
						movieName.substring(0);
					}
				}else{//非汉字开头
					if(movieList.get(i).getMovieName().length()>=8){
						//2166/神犬小七2HDTV19.mp4  2121/神犬小七2HDTV18.mp4 类似于这种名字
						//截取相同的部分
						movieName = movieList.get(i).getMovieName().substring(5,movieList.get(i).getMovieName().lastIndexOf(".")-2);
					}else{
						movieName.substring(0);
					}
				}
				if(i == 0){
					checkName = movieName;
					movieIdList.add(movieList.get(i).getMovieId());
				}else{
					if(movieName.equals(checkName)){//重复影片
						movieNameNum += 1;
						movieIdList.add(movieList.get(i).getMovieId());
					}else{//新影片
						if(movieNameNum >= 2){//名字重复出现3次及以上才认为是电视剧
							allUpdateMovieId.addAll(movieIdList);
							checkName = movieName;
							movieNameNum = 0;
							movieIdList.clear();
							movieIdList.add(movieList.get(i).getMovieId());
						}else{
							checkName = movieName;
							movieNameNum = 0;
							movieIdList.clear();
							movieIdList.add(movieList.get(i).getMovieId());
						}
					}
				}
			}
			if(!allUpdateMovieId.isEmpty()){
				movieMapper.updateMovieInfoByMovieIdList(allUpdateMovieId);
			}
		} catch (Exception e) {
		}
	}
	@Override
	public Map<String, Object> selectMovieInfoByParam(PagingUtil pagingUtil, Map<String, Object> map) {
		try {
			//最终返回的数据集合
			List<List<MovieVo>> returnList = new ArrayList<>();
			//封装查询参数
			map.put("currentPage", pagingUtil.getCurrentPage());
			map.put("pageSize", pagingUtil.getPageSize());
			List<MovieVo> movieVoList =  movieMapper.selectAllMovieVo(map);
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
			int total = movieMapper.selectMovieVoCount(map);
			pagingUtil.setTotal(total);
			if(total%pagingUtil.getPageSize() == 0){
				pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize());
			}else{
				pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize()+1);
			}
			map.put("movieList", returnList);
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
			e.printStackTrace();
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
	
}
