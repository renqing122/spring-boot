package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.FixOrderDao;
import com.heartsuit.showcase.dao.RepairmanDao;
import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Repairman;
import com.heartsuit.showcase.service.RepairmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepairmanServiceImpl implements RepairmanService {
    private RepairmanDao repairmanDao;
    private FixOrderDao fixOrderDao;
    @Autowired
    public RepairmanServiceImpl(RepairmanDao repairmanDao, FixOrderDao fixOrderDao){
        this.repairmanDao = repairmanDao;
        this.fixOrderDao = fixOrderDao;
    }
    @Override
    public String login(Repairman repairman) throws Exception {
        long repairmanByEmail = repairmanDao.findRepairmanByEmail(repairman);
        long repairmanByEmailAndPassword = repairmanDao.findRepairmanByEmailAndPassWord(repairman);
        return repairmanByEmail > 0 ? (repairmanByEmailAndPassword > 0 ? "0" : "1") : "2";
    }

    @Override
    public Repairman getId(Repairman repairman) {
        return repairmanDao.getRepairmanByEmail(repairman);
    }

    @Override
    public List<FixOrder> queryAllFixOrder(FixOrder fixOrder) {
        return fixOrderDao.findFixOrderByRepairmanId(fixOrder);
    }

    @Override
    public List<FixOrder> queryFixOrderByOrderStatus(FixOrder fixOrder) {
        return fixOrderDao.findFixOrderByRepairmanIdAndOrderStatus(fixOrder);
    }

    @Override
    public void updateOrderStatusByRepairmanId(FixOrder fixOrder) {
        fixOrderDao.updateOrderStatusByFixOrderId(fixOrder);
        repairmanDao.minusTaskByRepairmanId(repairmanDao.createRepairmanByRepairmanId(fixOrder.getRepairmanId()));
    }

    @Override
    public FixOrder findFixOrderByFixOrderId(FixOrder fixOrder) {
        return fixOrderDao.findFixOrderByFixOrderId(fixOrder);
    }
}
