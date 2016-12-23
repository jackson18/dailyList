package com.qijiabin.dailyList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.qijiabin.dailyList.entity.Target;
import com.qijiabin.dailyList.support.MyPipeline;
import com.qijiabin.dailyList.util.Constants;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午8:36:06
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MeiTuan implements PageProcessor {

	private Site site = Site.me()
			.setDomain("tech.meituan.com")
			.setUserAgent(Constants.USER_AGENT)
			.setCharset(Constants.CHARSET)
			.setSleepTime(Constants.SLEEP_TIME)
			.setRetryTimes(Constants.RETRY_TIMES);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	// 详情页的正则表达式
	public static final String URL_POST = "http://tech\\.meituan\\.com/\\w+\\.html";
	// 列表页的正则表达式
	public static final String URL_LIST = "http://tech\\.meituan\\.com/\\?l=\\w+";
	

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		try {
			// 列表页
			if (page.getUrl().regex(URL_LIST).match()) {
				List<String> list = page.getHtml().links().regex(URL_POST).all();
				for (String string : list) {
					System.out.println(string);
				}
				page.addTargetRequests(list);
				// 详情页
			} else {
				Selectable selectable = page.getHtml().xpath("//article/header/");
				Date date = sdf.parse(selectable.xpath("//p/span[@class='date']/text()").toString());
				if (sdf2.parse(Constants.COMPARE_DATE).before(date)) {
					Target t = new Target();
					t.setUrl(page.getUrl().toString());
					t.setTitle(selectable.xpath("//h1/text()").toString());
					t.setTime(sdf2.format(date).toString());
					page.putField("target", t);
					System.out.println();
				} else {
					page.setSkip(true);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void run() {
		Spider.create(new MeiTuan()).addUrl("http://tech.meituan.com/?l=10") // 开始地址
			.addPipeline(new MyPipeline()) // 打印到控制台
			.addPipeline(new FilePipeline("D:\\calvin1978")) // 保存到文件夹
			.thread(3) // 开启3线程
			.run();
	}

}

