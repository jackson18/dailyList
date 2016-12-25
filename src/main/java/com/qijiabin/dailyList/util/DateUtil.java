package com.qijiabin.dailyList.util;

import java.util.Calendar;
import java.util.Date;

/**
 * ========================================================
 * 日 期：2016年12月25日 上午9:58:39
 * 作 者：jackson
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class DateUtil {
	
	/** 
	   * 得到几天前的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	  public static Date getDateBefore(Date d,int day){  
	   Calendar now =Calendar.getInstance();  
	   now.setTime(d);  
	   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
	   return now.getTime();  
	  }  
	    
	  /** 
	   * 得到几天后的时间 
	   * @param d 
	   * @param day 
	   * @return 
	   */  
	  public static Date getDateAfter(Date d,int day){  
	   Calendar now =Calendar.getInstance();  
	   now.setTime(d);  
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);  
	   return now.getTime();  
	  }

}
