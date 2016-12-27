package com.qijiabin.dailyList;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
 * 日 期：2016年12月23日 下午8:36:06
 * 版 本：1.0.0
 * 类说明：美团技术
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
	// 详情页的正则表达式
	public static final String URL_POST = "http://tech\\.meituan\\.com/[a-zA-Z_-]+\\.html";
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
				page.addTargetRequests(list);
			// 详情页
			} else {
				Selectable selectable = page.getHtml().xpath("//article/header/");
				Date date = DateUtils.parseDate(selectable.xpath("//p/span[@class='date']/text()").toString(), "yyyy-MM-dd HH:mm");
				if (DateUtil.getDateBefore(new Date(), Constants.INTERVAL_DAY).before(date)) {
					Target t = new Target();
					t.setUrl(page.getUrl().toString());
					t.setTitle(selectable.xpath("//h1/text()").toString());
					t.setTime(DateFormatUtils.format(date, "yyyy-MM-dd"));
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
		Spider.create(new MeiTuan()).addUrl("http://tech.meituan.com/?l=10") // 开始地址
			.addPipeline(new MyPipeline()) // 打印到控制台
			.thread(5) // 开启5线程
			.run();
	}
	
}

