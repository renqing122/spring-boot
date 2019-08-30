package com.heartsuit.showcase.modules.web.controller;

import java.util.HashMap;
import java.util.List;

import com.heartsuit.showcase.domain.*;
import com.heartsuit.showcase.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    private RoomService roomService;
    private TenantService tenantService;
    private IMailService iMailService;
    private RentOrderService rentOrderService;
    private SystemMasterService systemMasterService;
    private OperatorService operatorService;
    private RepairmanService repairmanService;
    @Autowired
    public GreetingController(RoomService roomService,TenantService tenantService,IMailService iMailService,RentOrderService rentOrderService, SystemMasterService systemMasterService, OperatorService operatorService, RepairmanService repairmanService) {
        this.roomService = roomService;
        this.tenantService = tenantService;
        this.iMailService = iMailService;
        this.rentOrderService = rentOrderService;
        this.systemMasterService = systemMasterService;
        this.operatorService = operatorService;
        this.rentOrderService = rentOrderService;
    }

 ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //tenant
    /**
     * 新增租客 注册
     * @param tenant 租客信息
     * @return 新增是否成功
     */
    @RequestMapping(value = "/tenant/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestBody Tenant tenant) {
        String flag = this.tenantService.insert(tenant);
        if(flag.equals("0")){
            String code=tenantService.findCodeByEmail(tenant).getCode();
            iMailService.sendHtmlMail(tenant.getEmail(),"青年租房管理系统账户激活","<a href=\"http://114.116.9.214:8000/tenant/checkCode?code="+code+"\">激活请点击:"+code+"</a>");
            return "0"; //插入成功 发送邮件成功
        }
        else {
            return "1"; //插入失败 邮箱已存在
        }
    }

    /**
     * 查询所有租客
     * @return 所有租客信息
     */
    @RequestMapping(value = "/tenant/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Tenant> query() {
        return tenantService.findAll();
    }

    /**
     * 更新邮箱激活状态
     * @param code 租客
     * @return 更新是否成功
     */
    @RequestMapping(value = "/tenant/checkCode",method = RequestMethod.POST)
    @ResponseBody
    public String updateActivationStatus(String code) {
        tenantService.updateActivationStatus(tenantService.findEmailByCode(tenantService.createTenantByCode(code)));
        return "您已成功激活！";
    }

    /**
     * 检查邮箱是否重复
     * @param tenant 租客信息
     * @exception
     * @return
     */
    @RequestMapping(value = "/tenant/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmail(@RequestBody Tenant tenant) {
        return tenantService.checkEmail(tenant);
    }

    /**
     * 登录
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/tenant/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Tenant tenant) {
        return tenantService.login(tenant);
    }


    /**
     * 修改level
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/tenant/updateTenantLevelByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public void updateTenantLevel(@RequestBody Tenant tenant) {
        tenantService.updateTenantByLevel(tenant);
    }

    /**
     * 根据tenantId找邮箱
     * @param tenant 租客
     * @return 更新是否成功
     */
    @RequestMapping(value = "/tenant/queryTenantIdByEmail",method = RequestMethod.POST)
    @ResponseBody
    public String updateActivationStatus(@RequestBody Tenant tenant) {
        return tenantService.findTenantIdByEmail(tenant);
    }
    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //Room
    /**
     * 新增房间
     * @param room 房间信息
     */
    @RequestMapping(value = "/room/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody Room room) {
        roomService.insert(room);
    }

    /**
     * 顾客查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/room/tenantQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query1() {
        return roomService.TenantFindAll();
    }

    /**
     * 客服查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/room/operatorQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query2() {
        return roomService.OperatorFindAll();
    }


    /**
     * 根据type查询房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/room/queryRoomByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Room> queryType(@RequestBody Room room) {
        return roomService.findRoomByType(room);
    }
    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //RentOrder
    /**
     * 新增订单
     * @param rentOrder 订单信息
     */
    @RequestMapping(value = "/rentOrder/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody RentOrder rentOrder) {
        rentOrderService.insert(rentOrder);
    }

    /**
     * 查询所有订单信息
     * @return 所有房间信息
     */
    @RequestMapping(value = "/rentOrder/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<RentOrder> query123() {
        return rentOrderService.findAll();
    }

    /**
     * tenant根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @RequestMapping(value = "/rentOrder/tenantQueryRentOrderByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType1(@RequestBody RentOrder rentOrder) {
        return rentOrderService.TenantFindRentOrderByTenantId(rentOrder);
    }

    /**
     * operator根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @RequestMapping(value = "/rentOrder/operatorQueryRentOrderByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType2(@RequestBody RentOrder rentOrder) {
        return rentOrderService.OperatorFindRentOrderByTenantId(rentOrder);
    }
    /**
     * 根据orderId查找合同信息
     * @param rentOrder
     * @return Contract 合同信息
     */
    @RequestMapping(value = "/rentOrder/queryContractByRentOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract queryContractByRentOrderId(@RequestBody RentOrder rentOrder) {
        return rentOrderService.findContractByRentOrderId(rentOrder);
    }

    @RequestMapping(value = "/rentOrder/abandonedRentOrderByRentOrder", method = RequestMethod.POST)
    @ResponseBody
    public void abandonedRentOrderByRentOrder(@RequestBody RentOrder rentOrder) {
        rentOrderService.abandonedRentOrder(rentOrder);
    }

    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    /**
     * 管理员登录
     * @param systemMaster
     * @return “0”成功 “1”失败
     */
    @RequestMapping(value = "/systemMaster/login", method = RequestMethod.POST)
    @ResponseBody
    public String systemMasterLogin(@RequestBody SystemMaster systemMaster){
        return systemMasterService.login(systemMaster);
    }

    @RequestMapping(value = "/systemMaster/queryAllOperatorByActivationStatus0", method = RequestMethod.GET)
    @ResponseBody
    public List<Operator> systemMasterQueryAllOperatorByActivationStatus0(){
        return operatorService.findAllOperatorByActivationStatus0();
    }

    @RequestMapping(value = "/systemMaster/updateOperatorActivationStatus", method = RequestMethod.POST)
    @ResponseBody
    public String systemMasterUpdateOperatorActivationStatus(@RequestBody Operator operator){
        operatorService.updateOperatorActivationStatus(operator);
        return "true";
    }

    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////

    /**
     * 新增客服
     * @param operator
     * @return 新增是否成功
     */
    @RequestMapping(value = "/operator/insert", method = RequestMethod.POST)
    @ResponseBody
    public String operatorInsert(@RequestBody Operator operator){
        return this.operatorService.insert(operator);
    }

    /**
     * 查询所有客服
     * @return 所有客服信息
     */
    @RequestMapping(value = "/operator/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Operator> operatorQuery() {
        return operatorService.findAll();
    }

    /**
     * 登录
     * @param operator
     * @return “0”成功 “1”账号或密码错误 “2”账号不存在
     */
    @RequestMapping(value = "/operator/login", method = RequestMethod.POST)
    @ResponseBody
    public String operatorLogin(@RequestBody Operator operator){
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
    @RequestMapping(value = "/operator/queryContractByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract operatorQueryContractByOrderId(@RequestBody RentOrder rentOrder){
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
    @RequestMapping(value = "/operator/roomInsert", method = RequestMethod.POST)
    @ResponseBody
    public void operatorRoomInsert(@RequestBody Room room) {
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

    @RequestMapping(value = "/operator/insertRepairman",method = RequestMethod.POST)
    @ResponseBody
    public String operatorInsertRepairman(@RequestBody Repairman repairman){
        return operatorService.insertRepairman(repairman);
    }

    @RequestMapping(value = "/operator/queryAllFixOrder",method = RequestMethod.GET)
    @ResponseBody
    public List<FixOrder> operatorQueryAllFixOrder(){
        return operatorService.queryAllFixOrder();
    }

    @RequestMapping(value = "/operator/queryFixOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> operatorQueryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return operatorService.queryFixOrderByOrderStatus(fixOrder);
    }

    @RequestMapping(value = "/operator/queryAllRepairman",method = RequestMethod.GET)
    @ResponseBody
    public List<Repairman> operatorQueryAllRepairman(){
        return operatorService.queryAllRepairman();
    }

    @RequestMapping(value = "/operator/queryRepairmanByArea",method = RequestMethod.POST)
    @ResponseBody
    public List<Repairman> operatorQueryRepairmanByArea(@RequestBody Repairman repairman){
        return operatorService.queryRepairmanByArea(repairman);
    }

    @RequestMapping(value = "/operator/chooseRepairman",method = RequestMethod.POST)
    @ResponseBody
    public void operatorChooseRepairman(@RequestBody FixOrder fixOrder){
        operatorService.chooseRepairman(fixOrder);
    }

    @RequestMapping(value = "/operator/queryComplainOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<ComplainOrder> operatorQueryComplainOrderByOrderStatus(@RequestBody ComplainOrder complainOrder){
        return operatorService.queryComplainOrderByOrderStatus(complainOrder);
    }

    @RequestMapping(value = "/operator/updateOperatorResponse",method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateOperatorResponse(@RequestBody ComplainOrder complainOrder){
        operatorService.updateOperatorResponse(complainOrder);
    }


    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////

    /**
     * 登录
     * @param repairman
     * @return “0”成功 “1”账号或密码错误 “2”账号不存在
     */
    @RequestMapping(value = "/repairman/login", method = RequestMethod.POST)
    @ResponseBody
    public String repairmanLogin(@RequestBody Repairman repairman){
        return repairmanService.login(repairman);
    }

    @RequestMapping(value = "/repairman/getId", method = RequestMethod.POST)
    @ResponseBody
    public String repairmanGetRepairmanByEmailAndPassWord(@RequestBody Repairman repairman){
        return repairmanService.getId(repairman).getRepairmanId();
    }

    @RequestMapping(value = "/repairman/queryAllFixOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> repairmanQueryAllFixOrder(@RequestBody FixOrder fixOrder){
        return repairmanService.queryAllFixOrder(fixOrder);
    }

    @RequestMapping(value = "/repairman/queryFixOrderByOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> repairmanQueryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return repairmanService.queryFixOrderByOrderStatus(fixOrder);
    }

    @RequestMapping(value = "/repairman/updateOrderStatusByRepairmanId", method = RequestMethod.POST)
    @ResponseBody
    public void repairmanUpdateOrderStatusByRepairmanId(@RequestBody FixOrder fixOrder){
        repairmanService.updateOrderStatusByRepairmanId(fixOrder);
    }


    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////





    @GetMapping("/greeting")
    public String Active() {
        return "Hello DevCloud.";
    }

    @PostMapping("/greeting")
    public String greeting(@RequestBody HashMap<String, String> params) {
        return "Hello DevOps. Welcome " + params.get("name");
    }

}