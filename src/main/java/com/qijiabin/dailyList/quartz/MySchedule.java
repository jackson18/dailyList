package com.qijiabin.dailyList.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.qijiabin.dailyList.util.Constants;

/**
 * ========================================================
 * 日 期：2016年12月27日 上午11:21:36
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MySchedule {
	
	public static void start() {
		try {
			// 1、创建JobDetial对象
			JobDetail jobDetail = new JobDetail();
			jobDetail.setJobClass(MyJob.class);
			jobDetail.setName("MyJob");

			// 2、创建trigger对象
			CronTrigger trigger = new CronTrigger("MyCronTrigger", "MyTriggerGroup");  
			trigger.setCronExpression(Constants.CRON_EXPRESSION_Test);
			
			// 3、创建Scheduler对象，并配置JobDetail和Trigger对象
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			
			// 4、并执行启动、关闭等操作
			scheduler.start();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}

