package com.qijiabin.dailyList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午6:03:23
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class calvin1978 implements PageProcessor{
	
	private Site site = Site.me().setSleepTime(1000).setRetryTimes(3);
	private SimpleDateFormat sdf = new SimpleDateFormat("MM月 dd, yyyy");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	//列表页的正则表达式
	public static final String URL_LIST = "http://calvin1978\\.blogcn\\.com/page/\\d+";
	//详情页的正则表达式
    public static final String URL_POST = "http://calvin1978\\.blogcn\\.com/articles/\\w+\\.html";
	
	@Override
	public Site getSite() {
		return site;
	}
	
	@Override
	public void process(Page page) {
		try {
			//列表页
			if (page.getUrl().regex(URL_LIST).match()) {
				List<String> l_post = page.getHtml().links().regex(URL_POST).all(); //目标详情
				List<String> l_url = page.getHtml().links().regex(URL_LIST).all();	//所有的列表
			    page.addTargetRequests(l_post);
			    page.addTargetRequests(l_url);
			//详情页
			} else {
				Selectable selectable = page.getHtml().xpath("//article/header/");
			    Date date = sdf.parse(selectable.xpath("//p/time/text()").toString());
			    if (sdf2.parse("2016-11-01").before(date)) {
			    	page.putField("url", page.getUrl());
			    	page.putField("title", selectable.xpath("//h1/text()"));
			    	page.putField("time", sdf2.format(date));
			    	System.out.println();
			    } else {
			    	page.setSkip(true);
			    }
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		Spider.create(new calvin1978())
			.addUrl("http://calvin1978.blogcn.com/page/1")	//开始地址	
			.addPipeline(new ConsolePipeline())	//打印到控制台
			.addPipeline(new FilePipeline("D:\\calvin1978"))	//保存到文件夹
			.thread(5)	//开启5线程
			.run();
	}
	
}