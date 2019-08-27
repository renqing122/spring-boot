package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.RentOrderDao;
import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.service.RentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentOrderServiceImpl implements RentOrderService {
    private RentOrderDao rentOrderDao;
    @Autowired
    public RentOrderServiceImpl(RentOrderDao rentOrderDao) {
        this.rentOrderDao = rentOrderDao;
    }

    @Override
    public void insert(RentOrder rentOrder) {
        rentOrderDao.insert(rentOrder);
    }

    @Override
    public List<RentOrder> findAll() {
        return rentOrderDao.findAll();
    }

    @Override
    public List<RentOrder> findRentOrderByUserId(RentOrder rentOrder) {
        return rentOrderDao.findRentOrderByUserId(rentOrder);
    }

    @Override
    public Contract findContractByRentOrderId(RentOrder rentOrder) {
        return rentOrderDao.createContractByRentOrderId(rentOrder);
    }
}
