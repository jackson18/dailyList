package com.qijiabin.dailyList.support;

import java.util.Map;

import com.qijiabin.dailyList.entity.Target;
import com.qijiabin.dailyList.quartz.MyJob;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * ========================================================
 * 日 期：2016年12月27日 上午10:21:35
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MyPipeline implements Pipeline{

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        	MyJob.list.add((Target) entry.getValue());
        }
    }

}