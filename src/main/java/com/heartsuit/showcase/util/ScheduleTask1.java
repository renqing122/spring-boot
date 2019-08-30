package com.heartsuit.showcase.util;

import com.heartsuit.showcase.dao.RentOrderDao;
import com.heartsuit.showcase.domain.RentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Configuration
@EnableScheduling

public class ScheduleTask1{
    private RentOrderDao rentOrderDao;
    @Autowired
    public ScheduleTask1(RentOrderDao rentOrderDao) {
        this.rentOrderDao = rentOrderDao;
    }
    @Scheduled(cron = "0 0 12 25 * ?")
    private void configureTasks(){
        System.err.println("执行静态任务时间:"+ LocalDateTime.now());
    }
}
