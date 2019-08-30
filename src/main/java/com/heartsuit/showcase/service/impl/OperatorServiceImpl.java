package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.ComplainOrderDao;
import com.heartsuit.showcase.dao.FixOrderDao;
import com.heartsuit.showcase.dao.OperatorDao;
import com.heartsuit.showcase.dao.RepairmanDao;
import com.heartsuit.showcase.domain.ComplainOrder;
import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Operator;
import com.heartsuit.showcase.domain.Repairman;
import com.heartsuit.showcase.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorServiceImpl implements OperatorService {
    private OperatorDao operatorDao;
    private RepairmanDao repairmanDao;
    private FixOrderDao fixOrderDao;
    private ComplainOrderDao complainOrderDao;
    @Autowired
    public OperatorServiceImpl(OperatorDao operatorDao, RepairmanDao repairmanDao, FixOrderDao fixOrderDao, ComplainOrderDao complainOrderDao){
        this.operatorDao = operatorDao;
        this.repairmanDao = repairmanDao;
        this.fixOrderDao = fixOrderDao;
        this.complainOrderDao = complainOrderDao;
    }

    @Override
    public String insert(Operator operator) {
        if(operatorDao.findRepeatEmail(operator).equals("0")){
            operatorDao.insert(operator);
            return "0";
        }
        else {
            return "1";
        }
    }

    @Override
    public List<Operator> findAll() {
        return operatorDao.findAll();
    }

    @Override
    public String login(Operator operator) {
        long operatorByEmail = operatorDao.findOperatorByEmail(operator);
        long operatorByEmailAndPassword = operatorDao.findOperatorByEmailAndPassWord(operator);
        return operatorByEmail > 0 ? (operatorByEmailAndPassword > 0 ? "0" : "1") : "2";
    }

    @Override
    public Operator getId(Operator operator) {
        return operatorDao.getOperatorByEmail(operator);
    }

    @Override
    public void updateOperatorActivationStatus(Operator operator) {
        operatorDao.updateActivationStatus(operator);
    }

    @Override
    public List<Operator> findAllOperatorByActivationStatus0() {
        return operatorDao.findAllOperatorByActivationStatus0();
    }

    @Override
    public String insertRepairman(Repairman repairman) {
        if(repairmanDao.findRepeatEmail(repairman).equals("0")){
            repairmanDao.insert(repairman);
            return "导入成功";
        }
        else{
            return "已存在该邮箱";
        }
    }

    @Override
    public List<FixOrder> queryAllFixOrder() {
        return fixOrderDao.findAll();
    }

    @Override
    public List<FixOrder> queryFixOrderByOrderStatus(FixOrder fixOrder) {
        return fixOrderDao.findFixOrderByOrderStatus(fixOrder);
    }

    @Override
    public List<Repairman> queryAllRepairman() {
        return repairmanDao.findAll();
    }

    @Override
    public List<Repairman> queryRepairmanByArea(Repairman repairman) {
        return repairmanDao.findAllByArea(repairman);
    }

    @Override
    public void chooseRepairman(FixOrder fixOrder) {
        fixOrderDao.updateRepairmanIdByFixOrderId(fixOrder);
        fixOrderDao.updateOrderStatusByFixOrderId(fixOrder);
        repairmanDao.addTaskByRepairmanId(repairmanDao.createRepairmanByRepairmanId(fixOrder.getRepairmanId()));
    }

    @Override
    public List<ComplainOrder> queryComplainOrderByOrderStatus(ComplainOrder complainOrder) {
        return complainOrderDao.findComplainOrderByOrderStatus(complainOrder);
    }

    @Override
    public void updateOperatorResponse(ComplainOrder complainOrder) {
        complainOrderDao.updateOrderStatusAndOperatorResponseByComplainOrderId(complainOrder);
    }
}
