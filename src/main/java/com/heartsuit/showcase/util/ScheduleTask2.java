package com.heartsuit.showcase.util;

import com.heartsuit.showcase.dao.RentOrderDao;
import com.heartsuit.showcase.dao.RoomDao;
import com.heartsuit.showcase.dao.TenantDao;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleTask2 {
    private RentOrderDao rentOrderDao;
    private RoomDao roomDao;
    private TenantDao tenantDao;
    @Autowired
    public ScheduleTask2(RentOrderDao rentOrderDao, RoomDao roomDao, TenantDao tenantDao) {
        this.rentOrderDao = rentOrderDao;
        this.roomDao = roomDao;
        this.tenantDao = tenantDao;
    }
    @Scheduled(cron = "0 0 12 25 * ?")
    private void configureTasks()
    {

        System.err.println("执行静态任务时间:"+ LocalDateTime.now());
    }
    //每天中午12点判断短租未交钱的 修改订单状态为逾期未支付，修改房间isAvailable，信用等级降一级；判断短租时间结束的，修改订单状态为已结束,修改房间isAvailable
    public void updateOrderStatusAndRoomAvailableByNotPaidAndRentByDay(){
        List<RentOrder> rentOrders = rentOrderDao.getRentOrderNotPaidAndRentByDay();
        for(RentOrder rentOrder : rentOrders){
            Tenant tenant = tenantDao.getTenantByTenantId(tenantDao.createTenantByTenantId(rentOrder.getTenantId()));
            int level = Integer.valueOf(tenant.getLevel());
            if(level>0){
                level--;
            }
            tenant.setLevel(String.valueOf(level));
            tenantDao.updateTenantLevel(tenant);
            rentOrder.setOrderStatus("逾期未支付");
            rentOrderDao.updateOrderStatusByOrderId(rentOrder);
            Room room = new Room();
            room.setRoomId(rentOrder.getRoomId());
            roomDao.updateRoomAvailable(room);
        }
    }
    public void updateOrderStatusAndRoomAvailableByFinishAndRentByDay(){
        List<RentOrder> rentOrders = rentOrderDao.getRentOrderFinishAndRentByDay();
        for(RentOrder rentOrder : rentOrders){
            rentOrder.setOrderStatus("逾期未支付");
            rentOrderDao.updateOrderStatusByOrderId(rentOrder);
            Room room = new Room();
            room.setRoomId(rentOrder.getRoomId());
            roomDao.updateRoomAvailable(room);
        }
    }
}
