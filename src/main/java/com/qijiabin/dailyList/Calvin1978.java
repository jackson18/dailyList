package com.qijiabin.dailyList;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.qijiabin.dailyList.entity.Target;
import com.qijiabin.dailyList.support.MyPipeline;
import com.qijiabin.dailyList.util.Constants;
import com.qijiabin.dailyList.util.DateUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午6:03:23
 * 版 本：1.0.0
 * 类说明：江南白衣
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Calvin1978 implements PageProcessor{
	
	private Site site = Site.me()
			.setDomain("calvin1978.blogcn.com")
			.setUserAgent(Constants.USER_AGENT)
			.setCharset(Constants.CHARSET)
			.setSleepTime(Constants.SLEEP_TIME)
			.setRetryTimes(Constants.RETRY_TIMES);
	//列表页的正则表达式
	public static final String URL_LIST = "http://calvin1978\\.blogcn\\.com/page/\\d+";
	//详情页的正则表达式
    public static final String URL_POST = "http://calvin1978\\.blogcn\\.com/articles/\\w+\\.html";
    public static final CopyOnWriteArrayList<Target> list = new CopyOnWriteArrayList<Target>();
	
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
//				List<String> l_url = page.getHtml().links().regex(URL_LIST).all();	//所有的列表
			    page.addTargetRequests(l_post);
//			    page.addTargetRequests(l_url);
			//详情页
			} else {
				Selectable selectable = page.getHtml().xpath("//article/header/");
			    Date date = DateUtils.parseDate(selectable.xpath("//p/time/text()").toString(), "MM月 dd, yyyy");
			    if (DateUtil.getDateBefore(new Date(), Constants.INTERVAL_DAY).before(date)) {
			    	Target t = new Target();
			    	t.setUrl(page.getUrl().toString());
			    	t.setTitle(selectable.xpath("//h1/text()").toString());
			    	t.setTime(DateFormatUtils.format(date, "yyyy-MM-dd").toString());
			    	page.putField("target", t);
			    } else {
			    	page.setSkip(true);
			    }
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}	
	}
	
	public static void run() {
		Spider.create(new Calvin1978())
			.addUrl("http://calvin1978.blogcn.com/page/1")	//开始地址	
			.addPipeline(new MyPipeline())	//打印到控制台
			.thread(5)	//开启5线程
			.run();
	}
	
}

