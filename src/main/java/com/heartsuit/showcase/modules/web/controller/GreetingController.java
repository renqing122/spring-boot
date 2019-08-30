package com.heartsuit.showcase.modules.web.controller;

import java.util.HashMap;
import java.util.List;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.service.IMailService;
import com.heartsuit.showcase.service.RentOrderService;
import com.heartsuit.showcase.service.RoomService;
import com.heartsuit.showcase.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    private RoomService roomService;
    private TenantService tenantService;
    private IMailService iMailService;
    private RentOrderService rentOrderService;
    @Autowired
    public GreetingController(RoomService roomService,TenantService tenantService,IMailService iMailService,RentOrderService rentOrderService) {
        this.roomService = roomService;
        this.tenantService = tenantService;
        this.iMailService = iMailService;
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









    @GetMapping("/greeting")
    public String Active() {
        return "Hello DevCloud.";
    }

    @PostMapping("/greeting")
    public String greeting(@RequestBody HashMap<String, String> params) {
        return "Hello DevOps. Welcome " + params.get("name");
    }

}