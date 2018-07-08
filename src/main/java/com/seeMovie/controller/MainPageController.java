package com.seeMovie.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.seeMovie.common.Page;
import com.seeMovie.common.PageParserTool;
import com.seeMovie.common.RequestAndResponseTool;
import com.seeMovie.common.link.LinkFilter;
import com.seeMovie.common.utils.PagingUtil;
import com.seeMovie.pojo.MovieVo;
import com.seeMovie.service.TestService;

/**
 * 
 * @author mym
 * @date   2018年7月6日下午10:11:40
 * 进入主页
 */
@Controller
@RequestMapping("/mainPage")
public class MainPageController {
	@Autowired
	TestService testService;
	//已访问的 url 集合  已经访问过的 主要考虑 不能再重复了 使用set来保证不重复;
    private static Set visitedUrlSet = new HashSet();
    //待访问的 url 集合  待访问的主要考虑 1:规定访问顺序;2:保证不提供重复的带访问地址;
    private static LinkedList unVisitedUrlQueue = new LinkedList();
    //初始化访问的网站
    private static String movieWebSite = "http://www.dytt8.net/html/gndy/";
	/**
	 * 进入主页面
	 */
	@RequestMapping("/mainPage")
	public ModelAndView toMainPage(PagingUtil pagingUtil){
		ModelAndView mv = new ModelAndView();
		//查找所有电影
		List<List<MovieVo>> movieList = testService.selectAllMovieVo(pagingUtil);//初始化时默认只查询30条  第一页
		int total = testService.selectMovieVoCount();//查询总数
		pagingUtil.setTotal(total);
		if(total%pagingUtil.getPageSize() == 0){
			pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize());
		}else{
			pagingUtil.setTotalPageSize(total/pagingUtil.getPageSize()+1);
		}
		mv.addObject("movieList", movieList);
		mv.addObject("pagingUtil", pagingUtil);
		mv.setViewName("mainPage");
		return mv;
	}
	@RequestMapping("/getHerf")
	public void getHerf(){
		crawling(new String[]{movieWebSite});
		/*List<String> aHrefList = new ArrayList<>();
		for (Elements elements : returnList) {
			for (Element element : elements) {
				aHrefList.add(element.toString());
			}
		}
		testService.insertAllaHrefByList(aHrefList,"http://www.dytt8.net");*/
	}
	/**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {
    	//List<Elements> returnList = new ArrayList<>();

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //定义过滤器，例如提取以 https://www.dy2018.com/html/gndy/dyzz/ 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith(movieWebSite)){
                    return true;
                }else{
                    return false;
                }
            }
        };

        //循环条件：待抓取的链接不空且每次最多抓取500的链接
        while (unVisitedUrlQueue!=null && unVisitedUrlQueue.size() <=500) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String)unVisitedUrlQueue.removeFirst();;
            if (visitUrl == null){
                continue;
            }

            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);

            //对page进行处理： 访问DOM的某个标签
            Elements elements = PageParserTool.select(page,"a");
            List<String> aHrefList = new ArrayList<>();
    		for (Element element : elements) {
    			aHrefList.add(element.toString());
    		}
    		testService.insertAllaHrefByList(aHrefList,movieWebSite);
            //returnList.add(es);
            /*if(!es.isEmpty()){
                System.out.println("下面将打印所有a标签： ");
                System.out.println(es);
            }*/

            //将文件保存
            //FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            //Links.addVisitedUrlSet(visitUrl);
            visitedUrlSet.add(visitUrl);

           
            if(unVisitedUrlQueue.size()<=500){
            	 //得到超链接
                Set<String> links = PageParserTool.getLinks(page,"a");
                for (String link : links) {
                    //Links.addUnvisitedUrlQueue(link);
                	if (link != null && !link.trim().equals("")  && !visitedUrlSet.contains(link)  && !unVisitedUrlQueue.contains(link)){
                        unVisitedUrlQueue.add(link);
                    }
                	if(unVisitedUrlQueue.size()<=500){//超过两千不在添加新链接
                		continue;
                	}else{
                		break;
                	}
                	}
                }
        }
		//return returnList;
    }
    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            unVisitedUrlQueue.add(seeds[i]);
        }
    }
}
