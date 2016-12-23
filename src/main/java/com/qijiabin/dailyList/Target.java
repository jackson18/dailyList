package com.qijiabin.dailyList;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午7:05:55
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Target {
	
	private String url;
	private String title;
	private String time;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Target [url=" + url + ", title=" + title + ", time=" + time + "]";
	}
}
