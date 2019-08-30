package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.service.RentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentOrder")
public class RentOrderController {
    private RentOrderService rentOrderService;

    @Autowired
    public RentOrderController(RentOrderService rentOrderService) {
        this.rentOrderService = rentOrderService;
    }
    /**
     * 新增订单
     * @param rentOrder 订单信息
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody RentOrder rentOrder) {
        rentOrderService.insert(rentOrder);
    }

    /**
     * 查询所有订单信息
     * @return 所有房间信息
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<RentOrder> query() {
        return rentOrderService.findAll();
    }

    /**
     * tenant根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @RequestMapping(value = "/tenantQueryRentOrderByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType1(@RequestBody RentOrder rentOrder) {
        return rentOrderService.TenantFindRentOrderByTenantId(rentOrder);
    }

    /**
     * operator根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @RequestMapping(value = "/operatorQueryRentOrderByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType2(@RequestBody RentOrder rentOrder) {
        return rentOrderService.OperatorFindRentOrderByTenantId(rentOrder);
    }
    /**
     * 根据orderId查找合同信息
     * @param rentOrder
     * @return Contract 合同信息
     */
    @RequestMapping(value = "/queryContractByRentOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract queryContractByRentOrderId(@RequestBody RentOrder rentOrder) {
        return rentOrderService.findContractByRentOrderId(rentOrder);
    }

    @RequestMapping(value = "/abandonedRentOrderByRentOrder", method = RequestMethod.POST)
    @ResponseBody
    public void abandonedRentOrderByRentOrder(@RequestBody RentOrder rentOrder) {
        rentOrderService.abandonedRentOrder(rentOrder);
    }

    @RequestMapping(value = "/reRentLongLeaseRoom", method = RequestMethod.POST)
    @ResponseBody
    public void reRentLongLeaseRoom(@RequestBody RentOrder rentOrder) {
        rentOrderService.reRentLongLeaseRoom(rentOrder);
    }
}