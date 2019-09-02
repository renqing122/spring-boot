package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.*;
import com.heartsuit.showcase.domain.*;
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
    private TenantDao tenantDao;
    private RentOrderDao rentOrderDao;
    private RoomDao roomDao;
    @Autowired
    public OperatorServiceImpl(OperatorDao operatorDao, RepairmanDao repairmanDao, FixOrderDao fixOrderDao, ComplainOrderDao complainOrderDao, TenantDao tenantDao, RentOrderDao rentOrderDao, RoomDao roomDao){
        this.operatorDao = operatorDao;
        this.repairmanDao = repairmanDao;
        this.fixOrderDao = fixOrderDao;
        this.complainOrderDao = complainOrderDao;
        this.tenantDao = tenantDao;
        this.rentOrderDao = rentOrderDao;
        this.roomDao = roomDao;
    }

    @Override
    public String insert(Operator operator) throws Exception {
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
    public String login(Operator operator) throws Exception {
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
    public String insertRepairman(Repairman repairman) throws Exception {
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

    @Override
    public Tenant queryTenantByTenantId(Tenant tenant) {
        return tenantDao.getTenantByTenantId(tenant);
    }

    @Override
    public Tenant queryTenantByEmail(Tenant tenant) {
        return tenantDao.getTenantByEmail(tenant);
    }

    @Override
    public void updateLevelByTenantId(Tenant tenant) {
        tenantDao.updateTenantLevel(tenant);
    }

    @Override
    public RentOrder queryRentOrderByOrderId(RentOrder rentOrder) {
        return rentOrderDao.getRentOrderByOrderId(rentOrder);
    }

    @Override
    public void updateOrderStatusByOrderId(RentOrder rentOrder) {
        rentOrderDao.updateOrderStatusByOrderId(rentOrder);
    }

    @Override
    public List<RentOrder> queryAllRentOrderByRentStatus(RentOrder rentOrder) {
        return rentOrderDao.findRentOrderByOrderStatus(rentOrder);
    }

    @Override
    public void updateRoomInformationByRoomId(Room room) {
        roomDao.updateRoomInformation(room);
    }

    @Override
    public void updateOperatorInformationByOperator(Operator operator) throws Exception {
        operatorDao.updateOperatorInformation(operator);
    }

    @Override
    public Operator queryOperatorByOperatorId(Operator operator) {
        return operatorDao.getOperatorByOperatorId(operator);
    }

    @Override
    public Room queryRoomByRoomId(Room room) {
        return roomDao.findRoomByRoomId(room);
    }

    @Override
    public List<RentOrder> queryRentOrderListByOrderId(RentOrder rentOrder) {
        return rentOrderDao.getRentOrderListByOrderId(rentOrder);
    }

    @Override
    public List<Tenant> queryTenantListByTenantId(Tenant tenant) {
        return tenantDao.getTenantListByTenantId(tenant);
    }

}
