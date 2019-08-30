package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.*;

import java.util.List;

public interface OperatorService {
    String insert(Operator operator);
    List<Operator> findAll();
    String login(Operator operator);
    Operator getId(Operator operator);
    void updateOperatorActivationStatus(Operator operator);
    List<Operator> findAllOperatorByActivationStatus0();
    String insertRepairman(Repairman repairman);
    List<FixOrder> queryAllFixOrder();
    List<FixOrder> queryFixOrderByOrderStatus(FixOrder fixOrder);
    List<Repairman> queryAllRepairman();
    List<Repairman> queryRepairmanByArea(Repairman repairman);
    void chooseRepairman(FixOrder fixOrder);
    List<ComplainOrder> queryComplainOrderByOrderStatus(ComplainOrder complainOrder);
    void updateOperatorResponse(ComplainOrder complainOrder);
    Tenant queryTenantByTenantId(Tenant tenant);
    Tenant queryTenantByEmail(Tenant tenant);
    void updateLevelByTenantId(Tenant tenant);
    RentOrder queryRentOrderByOrderId(RentOrder rentOrder);
    void updateOrderStatusByOrderId(RentOrder rentOrder);
    List<RentOrder> queryAllRentOrderByRentStatus(RentOrder rentOrder);
    void updateRoomInformationByRoomId(Room room);
    void updateOperatorInformationByOperator(Operator operator);
    Operator queryOperatorByOperatorId(Operator operator);
    Room queryRoomByRoomId(Room room);
    List<RentOrder> queryRentOrderListByOrderId(RentOrder rentOrder);
}
