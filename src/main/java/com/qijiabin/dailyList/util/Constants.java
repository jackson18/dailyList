package com.qijiabin.dailyList.util;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午8:40:27
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Constants {
	
	public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
	public static final String CHARSET = "UTF-8";
	public static final int SLEEP_TIME = 1000;
	public static final int RETRY_TIMES = 3;
	public static final int INTERVAL_DAY = 2;
	public static final String CRON_EXPRESSION = "0 0 5 * * ?"; // 每天早上5点执行一次
	public static final String CRON_EXPRESSION_Test = "0 0/5 * * * ?"; // 每5分钟执行一次
	public static final String EMAIL_FROM = "1067915332@qq.com";
	public static final String EMAIL_TO = "qijiabin18@126.com";
	public static final String EMAIL_QQ	= "1067915332";

}
