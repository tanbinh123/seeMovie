package com.seeMovie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.seeMovie.common.Page;
import com.seeMovie.common.PageParserTool;
import com.seeMovie.common.RequestAndResponseTool;
import com.seeMovie.common.link.LinkFilter;
import com.seeMovie.common.link.Links;
import com.seeMovie.common.util.FileTool;
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
	/**
	 * 进入主页面
	 */
	@RequestMapping("/mainPage")
	public String toMainPage(){
		return "mainPage";
	}
	@RequestMapping("/getHerf")
	public void getHerf(){
		List<Elements> returnList = crawling(new String[]{"http://www.dytt8.net/"});
		List<String> aHrefList = new ArrayList<>();
		for (Elements elements : returnList) {
			for (Element element : elements) {
				aHrefList.add(element.toString());
			}
		}
		testService.insertAllaHrefByList(aHrefList);
	}
	/**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public List<Elements> crawling(String[] seeds) {
    	List<Elements> returnList = new ArrayList<>();

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //定义过滤器，提取以 http://www.dytt8.net/ 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www.dytt8.net/"))
                    return true;
                else
                    return false;
            }
        };

        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 1000) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }

            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);

            //对page进行处理： 访问DOM的某个标签
            Elements es = PageParserTool.select(page,"a");
            returnList.add(es);
            /*if(!es.isEmpty()){
                System.out.println("下面将打印所有a标签： ");
                System.out.println(es);
            }*/

            //将保存文件
            FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);

            //得到超链接
            Set<String> links = PageParserTool.getLinks(page,"img");
            for (String link : links) {
                Links.addUnvisitedUrlQueue(link);
                System.out.println("新增爬取路径: " + link);
            }
        }
		return returnList;
    }
    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }
}
