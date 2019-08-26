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
    public List<RentOrder> rentOrderQuery() {
        return rentOrderService.findAll();
    }

    /**
     * 根据userId查询订单信息
     * @return 满足条件的订单信息
     */
    @RequestMapping(value = "/rentOrder/queryRentOrderByUserId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType(@RequestBody RentOrder rentOrder) {
        return rentOrderService.findRentOrderByUserId(rentOrder);
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
    /**
     * 新增租客
     * @param tenant 租客信息
     * @return 新增是否成功
     */
    @RequestMapping(value = "/tenant/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestBody Tenant tenant) {
        String flag = this.tenantService.insert(tenant);
        if(flag.equals("0")){
            try{
                String code=tenantService.findCodeByEmail(tenant).getCode();
                iMailService.sendHtmlMail(tenant.getEmail(),"青年租房管理系统账户激活","<a href=\"http://localhost:8081/tenant/checkCode?code="+code+"\">激活请点击:"+code+"</a>");
                return "0";
            }catch (Exception e) {
                return e.getMessage();
            }
        }
        else {
            return "1";
        }
    }

    /**
     * 查询所有租客
     * @return 所有租客信息
     */
    @RequestMapping(value = "/tenant/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Tenant> tenantQuery() {
        return tenantService.findAll();
    }

    /**
     * 更新邮箱激活状态
     * @param code 租客
     * @return 更新是否成功
     */
    @RequestMapping(value = "/tenant/checkCode",method = RequestMethod.GET)
    @ResponseBody
    public String updateActivationStatus(String code) {
        tenantService.updateActivationStatus(tenantService.findEmailByCode(tenantService.createTenantByCode(code)));
        return "true";
    }

    /**
     * 检查邮箱是否重复
     * @param tenant 租客信息
     * @exception
     * @return
     */
    @RequestMapping(value = "/tenant/checkEmail", method = RequestMethod.GET)
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
     * 新增房间
     * @param room 房间信息
     */
    @RequestMapping(value = "/room/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody Room room) {
        roomService.insert(room);
    }

    /**
     * 查询所有房间
     * @return 所有房间信息
     */
    @RequestMapping(value = "/room/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> roonQuery() {
        return roomService.findAll();
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

    @GetMapping("/greeting")
    public String Active() {
        return "Hello DevCloud.";
    }

    @PostMapping("/greeting")
    public String greeting(@RequestBody HashMap<String, String> params) {
        return "Hello DevOps. Welcome " + params.get("name");
    }

}