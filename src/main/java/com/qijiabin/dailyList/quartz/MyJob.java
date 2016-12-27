package com.qijiabin.dailyList.quartz;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qijiabin.dailyList.Calvin1978;
import com.qijiabin.dailyList.Importnew;
import com.qijiabin.dailyList.Manong;
import com.qijiabin.dailyList.MeiTuan;
import com.qijiabin.dailyList.Tuicool;
import com.qijiabin.dailyList.email.SendEmail;
import com.qijiabin.dailyList.entity.Target;

/**
 * ========================================================
 * 日 期：2016年12月27日 上午11:20:31
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MyJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(MyJob.class);
	public static final CopyOnWriteArrayList<Target> list = new CopyOnWriteArrayList<Target>();
	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("{} : 开始执行定时任务...", new Date());
		Calvin1978.run();
		MeiTuan.run();
		Tuicool.run();
		Manong.run();
		Importnew.run();
		
		log.info("************结果如下***************");
		StringBuilder sb = new StringBuilder();
		for (Target t : list) {
			System.out.println(t);
			sb.append(t).append("<br/>");
		}
		SendEmail.send(sb.toString());
		list.clear();
		log.info("{} : 定时任务结果...", new Date());
	}
	
}