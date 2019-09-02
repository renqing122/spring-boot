package com.heartsuit.showcase.util;

import com.heartsuit.showcase.dao.RentOrderDao;
import com.heartsuit.showcase.dao.TenantDao;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Configuration
@EnableScheduling

public class ScheduleTask1{
    private RentOrderDao rentOrderDao;
    private TenantDao tenantDao;
    private IMailService iMailService;
    @Autowired
    public ScheduleTask1(IMailService iMailService, RentOrderDao rentOrderDao,TenantDao tenantDao) {
        this.iMailService = iMailService;
        this.rentOrderDao = rentOrderDao;
        this.tenantDao = tenantDao;
    }
    @Scheduled(cron = "0 0 12 25 * ?")
    private void configureTasks()
    {

        System.err.println("执行静态任务时间:"+ LocalDateTime.now());
    }
    //每月25号判断长租未交钱的发邮件
    public void sentEmailToInformPayment(){
        List<RentOrder> rentOrders = rentOrderDao.getRentOrderNotPaidAndRentByMonth();
        for(RentOrder rentOrder : rentOrders){
            iMailService.sendHtmlMail(tenantDao.getTenantByTenantId(tenantDao.createTenantByTenantId(rentOrder.getTenantId())).getEmail(),"青年租房管理系统付款提醒","您好，您需要在下月一号之前到前台缴纳本月应缴租金,逾期未支付，您的订单将关闭，信用等级也会被降级，将影响您的租房体验，谢谢合作！");
        }
    }
}
