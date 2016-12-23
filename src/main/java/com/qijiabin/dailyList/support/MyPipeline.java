package com.qijiabin.dailyList.support;

import java.util.Map;

import com.qijiabin.dailyList.Startup;
import com.qijiabin.dailyList.entity.Target;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPipeline implements Pipeline{

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + "  -------------------->  " + entry.getValue());
            Startup.list.add((Target) entry.getValue());
        }
    }

}