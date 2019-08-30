package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;

import java.util.List;

public interface RentOrderService {
    void insert(RentOrder rentOrder);

    List<RentOrder> findAll();

    List<RentOrder> TenantFindRentOrderByTenantId(RentOrder rentOrder);

    List<RentOrder> OperatorFindRentOrderByTenantId(RentOrder rentOrder);

    Contract findContractByRentOrderId(RentOrder rentOrder);

    String calculateRentMoney(String rentTime,String roomId,String rentType);

    void abandonedRentOrder(RentOrder rentOrder);

    void reRentLongLeaseRoom(RentOrder rentOrder);
}
