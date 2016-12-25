package com.qijiabin.dailyList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.qijiabin.dailyList.entity.Target;
import com.qijiabin.dailyList.support.MyPipeline;
import com.qijiabin.dailyList.util.Constants;
import com.qijiabin.dailyList.util.DateUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/*
 * ========================================================
 * 日 期：2016年12月25日 上午10:15:02
 * 作 者：jackson
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Tuicool implements PageProcessor {
	
	private Site site = Site.me()
			.setDomain("www.tuicool.com")
			.setUserAgent(Constants.USER_AGENT)
			.setCharset(Constants.CHARSET)
			.setSleepTime(Constants.SLEEP_TIME)
			.setRetryTimes(Constants.RETRY_TIMES);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	// 详情页的正则表达式
	public static final String URL_POST = "http://www\\.tuicool\\.com/mags/\\w{24}";
	// 列表页的正则表达式
	public static final String URL_LIST = "http://www\\.tuicool\\.com/mags\\?p=\\d+";
	

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
				Selectable selectable = page.getHtml().xpath("//div[@class='span8']/div/div/h3[@class='period-title']");
				String dateStr = selectable.xpath("//sub/text()").toString();
				Date date = sdf.parse(dateStr.substring(1, dateStr.length()-1));
				if (DateUtil.getDateBefore(new Date(), Constants.INTERVAL_DAY).before(date)) {
					Target t = new Target();
					t.setUrl(page.getUrl().toString());
					t.setTitle("编程狂人");
					t.setTime(sdf2.format(date).toString());
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
		Spider.create(new Tuicool()).addUrl("http://www.tuicool.com/mags?p=1") // 开始地址
			.addPipeline(new MyPipeline()) // 打印到控制台
			.thread(3) // 开启3线程
			.run();
	}
	
	public static void main(String[] args) {
		run();
	}
	
}
