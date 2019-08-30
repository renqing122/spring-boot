package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.ComplainOrder;
import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Operator;
import com.heartsuit.showcase.domain.Repairman;

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
}
