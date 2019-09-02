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
    public String insert(@RequestBody Operator operator) throws Exception {
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
    public String login(@RequestBody Operator operator) throws Exception {
        return operatorService.login(operator);
    }

    @RequestMapping(value = "/getId", method = RequestMethod.POST)
    @ResponseBody
    public String getOperatorByEmailAndPassWord(@RequestBody Operator operator){
        return operatorService.getId(operator).getOperatorId();
    }


    @RequestMapping(value = "/queryTenantByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public Tenant queryTenantByTenantId(@RequestBody Tenant tenant){
        return operatorService.queryTenantByTenantId(tenant);
    }

    @RequestMapping(value = "/queryTenantByEmail", method = RequestMethod.POST)
    @ResponseBody
    public Tenant queryTenantByEmail(@RequestBody Tenant tenant){
        return operatorService.queryTenantByEmail(tenant);
    }

    @RequestMapping(value = "/updateLevelByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public void updateLevelByTenantId(@RequestBody Tenant tenant){
        operatorService.updateLevelByTenantId(tenant);
    }


    @RequestMapping(value = "/queryRentOrderByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public RentOrder queryRentOrderByOrderId(@RequestBody RentOrder rentOrder){
        return operatorService.queryRentOrderByOrderId(rentOrder);
    }

    @RequestMapping(value = "/queryRentOrderListByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryRentOrderListByOrderId(@RequestBody RentOrder rentOrder){
        return operatorService.queryRentOrderListByOrderId(rentOrder);
    }

    @RequestMapping(value = "/queryTenantListByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<Tenant> queryTenantListByTenantId(@RequestBody Tenant tenant){
        return operatorService.queryTenantListByTenantId(tenant);
    }

    @RequestMapping(value = "/updateOrderStatusByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public void updateOrderStatusByOrderId(@RequestBody RentOrder rentOrder){
        operatorService.updateOrderStatusByOrderId(rentOrder);
    }

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

    /**
     * 新增房间
     * @param room 房间信息
     */
    @RequestMapping(value = "/roomInsert", method = RequestMethod.POST)
    @ResponseBody
    public void roomInsert(@RequestBody Room room) {
        roomService.insert(room);
    }


    @RequestMapping(value = "/queryAllRentOrderByOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryAllRentOrderByRentStatus(@RequestBody RentOrder rentOrder){
        return operatorService.queryAllRentOrderByRentStatus(rentOrder);
    }

    @RequestMapping(value = "/insertRepairman",method = RequestMethod.POST)
    @ResponseBody
    public String insertRepairman(@RequestBody Repairman repairman) throws Exception {
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

    @RequestMapping(value = "/updateRoomInformationByRoomId",method = RequestMethod.POST)
    @ResponseBody
    public void updateRoomInformationByRoomId(@RequestBody Room room){
        operatorService.updateRoomInformationByRoomId(room);
    }

    @RequestMapping(value = "/updateOperatorInformationByOperator",method = RequestMethod.POST)
    @ResponseBody
    public void updateOperatorInformationByOperator(@RequestBody Operator operator) throws Exception {
        operatorService.updateOperatorInformationByOperator(operator);
    }

    @RequestMapping(value = "/queryOperatorByOperatorId",method = RequestMethod.POST)
    @ResponseBody
    public Operator queryOperatorByOperatorId(@RequestBody Operator operator){
        return operatorService.queryOperatorByOperatorId(operator);
    }

    @RequestMapping(value = "/queryRoomByRoomId",method = RequestMethod.POST)
    @ResponseBody
    public Room queryRoomByRoomId(@RequestBody Room room){
        return operatorService.queryRoomByRoomId(room);
    }

}
