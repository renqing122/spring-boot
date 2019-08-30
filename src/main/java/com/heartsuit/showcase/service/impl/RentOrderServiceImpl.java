package com.heartsuit.showcase.service.impl;

import com.heartsuit.showcase.dao.RentOrderDao;
import com.heartsuit.showcase.dao.RoomDao;
import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.service.RentOrderService;
import com.heartsuit.showcase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.heartsuit.showcase.util.DateUtil;
import java.math.BigDecimal;
import java.util.List;

@Component
public class RentOrderServiceImpl implements RentOrderService {
    DateUtil dateUtil = new DateUtil();
    private RentOrderDao rentOrderDao;
    private RoomDao roomDao;
    @Autowired
    public RentOrderServiceImpl(RentOrderDao rentOrderDao,RoomDao roomDao) {
        this.rentOrderDao = rentOrderDao;
        this.roomDao = roomDao;
    }

    @Override
    public void insert(RentOrder rentOrder) {
        Room room = new Room();
        room.setRoomId(rentOrder.getRoomId());
        roomDao.updateRoomNotAvailable(room);
        rentOrder.setRentMoney(calculateRentMoney(rentOrder.getRentTime(),rentOrder.getRoomId(),rentOrder.getRentType()));
        rentOrderDao.insert(rentOrder);
    }

    @Override
    public List<RentOrder> findAll() {
        return rentOrderDao.findAll();
    }

    @Override
    public List<RentOrder> TenantFindRentOrderByTenantId(RentOrder rentOrder) {
        return rentOrderDao.tenantFindRentOrderByTenantId(rentOrder);
    }

    @Override
    public List<RentOrder> OperatorFindRentOrderByTenantId(RentOrder rentOrder) {
        return rentOrderDao.OperatorFindRentOrderByTenantId(rentOrder);
    }

    @Override
    public Contract findContractByRentOrderId(RentOrder rentOrder) {
        return rentOrderDao.createContractByRentOrderId(rentOrder);
    }

    @Override
    public String calculateRentMoney(String rentTime, String roomId,String rentType) {
        Room room = new Room();
        room.setRoomId(roomId);
        String price = roomDao.findRoomByRoomId(room).getPrice(); //获取房间单价
        if(StringUtil.convertNullToEmpty(price).equals(""))
            price = "0";
        if(StringUtil.convertNullToEmpty(rentTime).equals(""))
            rentTime = "0";
        if(rentType.equals("长期")) {
            return String.valueOf((new BigDecimal(dateUtil.getDaysOfMonth()).multiply(new BigDecimal(price)).multiply(new BigDecimal(0.8))).add(new BigDecimal(0.5)).setScale(0, BigDecimal.ROUND_DOWN).longValue()); //打8折
        }else
        return String.valueOf((new BigDecimal(rentTime).multiply(new BigDecimal(price))).longValue());
    }

    @Override
    public void abandonedRentOrder(RentOrder rentOrder) {
        rentOrderDao.AbandonRentOrder(rentOrder);
    }

    @Override
    public void reRentLongLeaseRoom(RentOrder rentOrder) {
        rentOrderDao.reRentUpdateEndDateAndRentTime(rentOrder);
    }
}
