package com.qijiabin.dailyList;

import java.util.concurrent.CopyOnWriteArrayList;

import com.qijiabin.dailyList.entity.Target;

/**
 * ========================================================
 * 日 期：2016年12月23日 下午8:36:57
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class Startup {
	
	public static final CopyOnWriteArrayList<Target> list = new CopyOnWriteArrayList<Target>();

	public static void main(String[] args) {
		Calvin1978.run();
		MeiTuan.run();
		Tuicool.run();
		Manong.run();
		Importnew.run();
		
		System.out.println("**********结果如下*********");
		for (Target t : list) {
			System.out.println(t);
		}
	}

}
