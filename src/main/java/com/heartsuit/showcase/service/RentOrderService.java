package com.heartsuit.showcase.service;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;

import java.util.List;

public interface RentOrderService {
    void insert(RentOrder rentOrder);

    List<RentOrder> findAll();

    List<RentOrder> findRentOrderByUserId(RentOrder rentOrder);

    Contract findContractByRentOrderId(RentOrder rentOrder);

}
