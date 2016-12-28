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

/*
 * ========================================================
 * 日 期：2016年12月25日 上午10:15:02
 * 作 者：jackson
 * 版 本：1.0.0
 * 类说明：阮一峰
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Ruanyifeng implements PageProcessor {
	
	private Site site = Site.me()
			.setDomain("www.ruanyifeng.com")
			.setUserAgent(Constants.USER_AGENT)
			.setCharset(Constants.CHARSET)
			.setSleepTime(Constants.SLEEP_TIME)
			.setRetryTimes(Constants.RETRY_TIMES);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		try {
			List<String> list = page.getHtml().xpath("//div[@id='alpha-inner']/div/div[@class='module-content']/ul/li/tidyText()").all();
			for (int i = 1; i <= list.size(); i++) {
				Selectable selectable = page.getHtml().xpath("//div[@id='alpha-inner']/div/div[@class='module-content']/ul/li["+i+"]");
				String dateStr = selectable.regex("(\\d{4}\\.\\d{2}\\.\\d{2})").toString();
				Date date = DateUtils.parseDate(dateStr, "yyyy.MM.dd");
				if (DateUtil.getDateBefore(new Date(), Constants.INTERVAL_DAY).before(date)) {
					Target t = new Target();
					t.setUrl(selectable.links().all().get(0));
					t.setTitle(selectable.xpath("//a/text()").toString());
					t.setTime(DateFormatUtils.format(date, "yyyy-MM-dd").toString());
					page.putField("target"+i, t);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void run() {
		Spider.create(new Ruanyifeng()).addUrl("http://www.ruanyifeng.com/blog/archives.html") // 开始地址
			.addPipeline(new MyPipeline()) // 打印到控制台
			.thread(5) // 开启5线程
			.run();
	}
	
}

