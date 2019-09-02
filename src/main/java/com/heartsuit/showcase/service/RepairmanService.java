package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.FixOrder;
import com.heartsuit.showcase.domain.Repairman;

import java.util.List;

public interface RepairmanService {
    String login(Repairman repairman) throws Exception;
    Repairman getId(Repairman repairman);
    List<FixOrder> queryAllFixOrder(FixOrder fixOrder);
    List<FixOrder> queryFixOrderByOrderStatus(FixOrder fixOrder);
    void updateOrderStatusByRepairmanId(FixOrder fixOrder);
    FixOrder findFixOrderByFixOrderId(FixOrder fixOrder);
}
