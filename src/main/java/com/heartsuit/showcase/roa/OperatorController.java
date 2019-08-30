package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.*;
import com.heartsuit.showcase.service.OperatorService;
import com.heartsuit.showcase.service.RentOrderService;
import com.heartsuit.showcase.service.RoomService;
import com.heartsuit.showcase.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    private OperatorService operatorService;
    private TenantService tenantService;
    private RentOrderService rentOrderService;
    private RoomService roomService;
    @Autowired
    public OperatorController(OperatorService operatorService, TenantService tenantService, RentOrderService rentOrderService,RoomService roomService){
        this.operatorService = operatorService;
        this.tenantService = tenantService;
        this.rentOrderService = rentOrderService;
        this.roomService = roomService;
    }

    /**
     * 新增客服
     * @param operator
     * @return 新增是否成功
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestBody Operator operator){
        return this.operatorService.insert(operator);
    }

    /**
     * 查询所有客服
     * @return 所有客服信息
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Operator> query() {
        return operatorService.findAll();
    }

    /**
     * 登录
     * @param operator
     * @return “0”成功 “1”账号或密码错误 “2”账号不存在
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Operator operator){
        return operatorService.login(operator);
    }

    @RequestMapping(value = "/getId", method = RequestMethod.POST)
    @ResponseBody
    public String getOperatorByEmailAndPassWord(@RequestBody Operator operator){
        return operatorService.getId(operator).getOperatorId();
    }

    /*/**
     *
     * @param tenantId
     * @return
     */
    /*缺TenantDao里由tenantId查找tenant
    @RequestMapping(value = "/queryTenantByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public Tenant findTenantByTenantId(@RequestBody Tenant tenant){
        return tenant;
    }*/

    /*/**
     *
     * @param email
     * @return
     */
    /*缺TenantDao里由email查找tenant
    @RequestMapping(value = "/queryTenantByEmail", method = RequestMethod.POST)
    @ResponseBody
    public Tenant findTenantByEmail(@RequestBody Tenant tenant){
        return tenant;
    }*/

    /*/**
     *
     * @param tenantId level
     */
    /*Tenant缺level属性,缺TenantDao里由tenantId查找对应tenant并修改level
    @RequestMapping(value = "/updateLevelByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public void updateLevelByTenantId(@RequestBody Tenant tenant){
    }*/

    /*/**
     *
     * @param tenantId
     * @return
     */
    /*缺RentOrderDao里由orderId查找rentOrder
    @RequestMapping(value = "/queryRentOrderByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public RentOrder queryRentOrderByOrderId(@RequestBody RentOrder rentOrder){
        return rentOrder;
    }*/

    /*/**
     *
     * @param orderId orderStatus
     */
    /*缺RentOrderDao里由orderId查找对应rentOrder并修改orderStatus
    @RequestMapping(value = "/updateOrderStatusByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public void updateOrderStatusByOrderId(@RequestBody RentOrder rentOrder){
    }*/

    /**
     * 根据orderId创建合同
     * @param rentOrder
     * @return
     */
    @RequestMapping(value = "/queryContractByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract queryContractByOrderId(@RequestBody RentOrder rentOrder){
        return rentOrderService.findContractByRentOrderId(rentOrder);
    }

    /*/**
     * 根据新合同修改orderId对应的订单
     * @param rentOrder
     * @return
     */
    /*缺RentOrderDao由orderId查找对应rentOrder并根据Contract的内容修改
    @RequestMapping(value = "/updateRentOrderByContract", method = RequestMethod.POST)
    @ResponseBody
    public void updateRentOrderByContract(Contract contract){
    }*/

    /**
     * 新增房间
     * @param room 房间信息
     */
    @RequestMapping(value = "/roomInsert", method = RequestMethod.POST)
    @ResponseBody
    public void roomInsert(@RequestBody Room room) {
        roomService.insert(room);
    }

    /*/**
     * 查找待审核的订单
     * @return rentOrders
     */
    /*缺RentOrderDao里查找orderStatus为0的rentOrders
    @RequestMapping(value = "/queryAllRentOrderByOrderStatus0", method = RequestMethod.GET)
    @ResponseBody
    public List<RentOrder> queryAllRentOrderByRentStatus0(){
        return rentOrders;
    }*/

    /*/**
     * 审核订单
     */
    /*缺RentOrderDao里根据orderId修改对应rentOrder的orderStatus
    @RequestMapping(value = "/updateOrderStatusByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryAllRentOrderByRentStatus0(@RequestBody RentOrder rentOrder){
    }*/

    @RequestMapping(value = "/insertRepairman",method = RequestMethod.POST)
    @ResponseBody
    public String insertRepairman(@RequestBody Repairman repairman){
        return operatorService.insertRepairman(repairman);
    }

    @RequestMapping(value = "/queryAllFixOrder",method = RequestMethod.GET)
    @ResponseBody
    public List<FixOrder> queryAllFixOrder(){
        return operatorService.queryAllFixOrder();
    }

    @RequestMapping(value = "/queryFixOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> queryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return operatorService.queryFixOrderByOrderStatus(fixOrder);
    }

    @RequestMapping(value = "/queryAllRepairman",method = RequestMethod.GET)
    @ResponseBody
    public List<Repairman> queryAllRepairman(){
        return operatorService.queryAllRepairman();
    }

    @RequestMapping(value = "/queryRepairmanByArea",method = RequestMethod.POST)
    @ResponseBody
    public List<Repairman> queryRepairmanByArea(@RequestBody Repairman repairman){
        return operatorService.queryRepairmanByArea(repairman);
    }

    @RequestMapping(value = "/chooseRepairman",method = RequestMethod.POST)
    @ResponseBody
    public void chooseRepairman(@RequestBody FixOrder fixOrder){
        operatorService.chooseRepairman(fixOrder);
    }

    @RequestMapping(value = "/queryComplainOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<ComplainOrder> queryComplainOrderByOrderStatus(@RequestBody ComplainOrder complainOrder){
        return operatorService.queryComplainOrderByOrderStatus(complainOrder);
    }

    @RequestMapping(value = "/updateOperatorResponse",method = RequestMethod.POST)
    @ResponseBody
    public void updateOperatorResponse(@RequestBody ComplainOrder complainOrder){
        operatorService.updateOperatorResponse(complainOrder);
    }
}
