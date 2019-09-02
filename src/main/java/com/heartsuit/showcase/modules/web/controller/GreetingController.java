package com.heartsuit.showcase.modules.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.heartsuit.showcase.domain.*;
import com.heartsuit.showcase.service.*;
import com.heartsuit.showcase.util.Result;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MongoTemplate mongoTemplate;
    @Autowired
    public GreetingController(RoomService roomService,TenantService tenantService,IMailService iMailService,RentOrderService rentOrderService, SystemMasterService systemMasterService, OperatorService operatorService, RepairmanService repairmanService, MongoTemplate mongoTemplate) {
        this.roomService = roomService;
        this.tenantService = tenantService;
        this.iMailService = iMailService;
        this.rentOrderService = rentOrderService;
        this.systemMasterService = systemMasterService;
        this.operatorService = operatorService;
        this.rentOrderService = rentOrderService;
        this.repairmanService  = repairmanService;
        this.mongoTemplate = mongoTemplate;
    }

 ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //tenant
    /**
     * 新增租客 注册
     * @param tenant 租客信息
     * @return 新增是否成功
     */
    @CrossOrigin
    @RequestMapping(value = "/tenant/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestBody Tenant tenant) throws Exception {
        String flag = this.tenantService.insert(tenant);
        if(flag.equals("0")){
            String code=tenantService.findCodeByEmail(tenant).getCode();
            iMailService.sendHtmlMail(tenant.getEmail(),"青年租房管理系统账户激活","<a href=\"http://114.116.9.214:8000/api/v1/tenant/checkCode?code="+code+"\">激活请点击:"+code+"</a>");
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
    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/tenant/checkCode",method = RequestMethod.GET)
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
    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/tenant/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Tenant tenant) throws Exception {
        return tenantService.login(tenant);
    }


    /**
     * 修改level
     * @param tenant
     * @return
     */
    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/tenant/queryTenantIdByEmail",method = RequestMethod.POST)
    @ResponseBody
    public String tenantQueryTenantIdByEmail(@RequestBody Tenant tenant) {
        return tenantService.findTenantIdByEmail(tenant);
    }

    @CrossOrigin
    @RequestMapping(value = "/tenant/insertFixOrder",method = RequestMethod.POST)
    @ResponseBody
    public void tenantInsertFixOrder(@RequestBody FixOrder fixOrder) {
        tenantService.insertFixOrder(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/tenant/insertComplainOrder",method = RequestMethod.POST)
    @ResponseBody
    public void tenantInsertComplainOrder(@RequestBody ComplainOrder complainOrder) {
        tenantService.insertComplainOrder(complainOrder);
    }

    @RequestMapping(value = "/tenant/updateTenantCommentByFixOrderId",method = RequestMethod.POST)
    @ResponseBody
    public void tenantUpdateTenantCommentByFixOrderId(@RequestBody FixOrder fixOrder){
        tenantService.updateTenantCommentByFixOrderId(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/tenant/updateTenantInformation",method = RequestMethod.POST)
    @ResponseBody
    public void tenantUpdateTenantInformation(@RequestBody Tenant tenant) throws Exception {
        tenantService.updateTenantInformationByOperator(tenant);
    }

    @CrossOrigin
    @RequestMapping(value = "/tenant/queryTenantByTenantId",method = RequestMethod.POST)
    @ResponseBody
    public Tenant tenantQueryTenantByTenantId(@RequestBody Tenant tenant){
        return tenantService.queryTenantByTenantId(tenant);
    }

    @RequestMapping(value = "/tenant/findComplainOrderByTenantId",method = RequestMethod.POST)
    @ResponseBody
    public List<ComplainOrder> tenantFindComplainOrderByTenantId(@RequestBody ComplainOrder complainOrder){
        return tenantService.findComplainOrderByTenantId(complainOrder);
    }

    @RequestMapping(value = "/tenant/findFixOrderByTenantId",method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> tenantFindFixOrderByTenantId(@RequestBody FixOrder fixOrder){
        return tenantService.findFixOrderByTenantId(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/tenant/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String tenantForgetPassword(@RequestBody Tenant tenant) {
        if(tenantService.forgetPassword(tenant).equals("0")){
            String tenantId=tenantService.findCodeByEmail(tenant).getTenantId();
            iMailService.sendHtmlMail(tenant.getEmail(),"青年租房管理系统密码重置","<a href=\"http://114.116.9.214:8000/api/v1/tenant/checkTenantId?tenantId="+tenantId+"\">重置请点击:"+tenantId+"</a>");
            return "0";
        }
        else {
            return "1";
        }
    }

    @RequestMapping(value = "/tenant/checkTenantId",method = RequestMethod.GET)
    @ResponseBody
    public String tenantReSetPassword(String tenantId) throws Exception {
        tenantService.reSetPasswordByTenantId(tenantService.createTenantByTenantId(tenantId));
        return "您已重置密码，重置后密码为000000";
    }
    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //Room
    /**
     * 新增房间
     * @param room 房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/room/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody Room room) {
        roomService.insert(room);
    }

    /**
     * 顾客查询所有房间
     * @return 所有房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/room/tenantQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query1() {
        return roomService.TenantFindAll();
    }

    /**
     * 客服查询所有房间
     * @return 所有房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/room/operatorQueryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Room> query2() {
        return roomService.OperatorFindAll();
    }


    /**
     * 根据type查询房间
     * @return 所有房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/room/queryRoomByType", method = RequestMethod.POST)
    @ResponseBody
    public List<Room> queryType(@RequestBody Room room) {
        return roomService.findRoomByType(room);
    }

    @CrossOrigin
    @RequestMapping(value = "/room/reSetRoomIsAbandoned", method = RequestMethod.POST)
    @ResponseBody
    public void roomReSetRoomIsAbandoned(@RequestBody Room room) {
        roomService.reSetRoomIsAbandoned(room);
    }

    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    //RentOrder
    /**
     * 新增订单
     * @param rentOrder 订单信息
     */
    @CrossOrigin
    @RequestMapping(value = "/rentOrder/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody RentOrder rentOrder) {
        rentOrderService.insert(rentOrder);
    }

    /**
     * 查询所有订单信息
     * @return 所有房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/rentOrder/queryAll", method = RequestMethod.GET)
    @ResponseBody
    public List<RentOrder> query123() {
        return rentOrderService.findAll();
    }

    /**
     * tenant根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @CrossOrigin
    @RequestMapping(value = "/rentOrder/tenantQueryRentOrderByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> queryType1(@RequestBody RentOrder rentOrder) {
        return rentOrderService.TenantFindRentOrderByTenantId(rentOrder);
    }

    /**
     * operator根据tenantId查询订单信息
     * @return 满足条件的订单信息
     */
    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/rentOrder/queryContractByRentOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract queryContractByRentOrderId(@RequestBody RentOrder rentOrder) {
        return rentOrderService.findContractByRentOrderId(rentOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/rentOrder/abandonedRentOrderByRentOrder", method = RequestMethod.POST)
    @ResponseBody
    public void abandonedRentOrderByRentOrder(@RequestBody RentOrder rentOrder) {
        rentOrderService.abandonedRentOrder(rentOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/rentOrder/reRentLongLeaseRoom", method = RequestMethod.POST)
    @ResponseBody
    public void rentOrderReRentLongLeaseRoom(@RequestBody RentOrder rentOrder) {
        rentOrderService.reRentLongLeaseRoom(rentOrder);
    }

    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
    /**
     * 管理员登录
     * @param systemMaster
     * @return “0”成功 “1”失败
     */////////////////////
    @CrossOrigin
    @RequestMapping(value = "/systemMaster/login", method = RequestMethod.POST)
    @ResponseBody
    public String systemMasterLogin(@RequestBody SystemMaster systemMaster){
        return systemMasterService.login(systemMaster);
    }

    @CrossOrigin
    @RequestMapping(value = "/systemMaster/queryAllOperatorByActivationStatus0", method = RequestMethod.GET)
    @ResponseBody
    public List<Operator> systemMasterQueryAllOperatorByActivationStatus0(){
        return operatorService.findAllOperatorByActivationStatus0();
    }

    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/operator/insert", method = RequestMethod.POST)
    @ResponseBody
    public String operatorInsert(@RequestBody Operator operator) throws Exception {
        return this.operatorService.insert(operator);
    }

    /**
     * 查询所有客服
     * @return 所有客服信息
     */
    @CrossOrigin
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
    @CrossOrigin
    @RequestMapping(value = "/operator/login", method = RequestMethod.POST)
    @ResponseBody
    public String operatorLogin(@RequestBody Operator operator) throws Exception {
        return operatorService.login(operator);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/getId", method = RequestMethod.POST)
    @ResponseBody
    public String operatorGetOperatorByEmailAndPassWord(@RequestBody Operator operator){
        return operatorService.getId(operator).getOperatorId();
    }


    @CrossOrigin
    @RequestMapping(value = "/operator/queryTenantByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public Tenant operatorQueryTenantByTenantId(@RequestBody Tenant tenant){
        return operatorService.queryTenantByTenantId(tenant);
    }

    @RequestMapping(value = "/operator/queryTenantListByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public List<Tenant> operatorQueryTenantListByTenantId(@RequestBody Tenant tenant){
        return operatorService.queryTenantListByTenantId(tenant);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryTenantByEmail", method = RequestMethod.POST)
    @ResponseBody
    public Tenant operatorQueryTenantByEmail(@RequestBody Tenant tenant){
        return operatorService.queryTenantByEmail(tenant);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/updateLevelByTenantId", method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateLevelByTenantId(@RequestBody Tenant tenant){
        operatorService.updateLevelByTenantId(tenant);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryRentOrderByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public RentOrder operatorQueryRentOrderByOrderId(@RequestBody RentOrder rentOrder){
        return operatorService.queryRentOrderByOrderId(rentOrder);
    }

    @RequestMapping(value = "/operator/queryRentOrderListByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> operatorQueryRentOrderListByOrderId(@RequestBody RentOrder rentOrder){
        return operatorService.queryRentOrderListByOrderId(rentOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/updateOrderStatusByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateOrderStatusByOrderId(@RequestBody RentOrder rentOrder){
        operatorService.updateOrderStatusByOrderId(rentOrder);
    }

    /**
     * 根据orderId创建合同
     * @param rentOrder
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/operator/queryContractByOrderId", method = RequestMethod.POST)
    @ResponseBody
    public Contract operatorQueryContractByOrderId(@RequestBody RentOrder rentOrder){
        return rentOrderService.findContractByRentOrderId(rentOrder);
    }

    /**
     * 新增房间
     * @param room 房间信息
     */
    @CrossOrigin
    @RequestMapping(value = "/operator/roomInsert", method = RequestMethod.POST)
    @ResponseBody
    public void operatorRoomInsert(@RequestBody Room room) {
        roomService.insert(room);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryAllRentOrderByOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<RentOrder> operatorQueryAllRentOrderByRentStatus(@RequestBody RentOrder rentOrder){
        return operatorService.queryAllRentOrderByRentStatus(rentOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/insertRepairman",method = RequestMethod.POST)
    @ResponseBody
    public String operatorInsertRepairman(@RequestBody Repairman repairman) throws Exception {
        return operatorService.insertRepairman(repairman);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryAllFixOrder",method = RequestMethod.GET)
    @ResponseBody
    public List<FixOrder> operatorQueryAllFixOrder(){
        return operatorService.queryAllFixOrder();
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryFixOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> operatorQueryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return operatorService.queryFixOrderByOrderStatus(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryAllRepairman",method = RequestMethod.GET)
    @ResponseBody
    public List<Repairman> operatorQueryAllRepairman(){
        return operatorService.queryAllRepairman();
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryRepairmanByArea",method = RequestMethod.POST)
    @ResponseBody
    public List<Repairman> operatorQueryRepairmanByArea(@RequestBody Repairman repairman){
        return operatorService.queryRepairmanByArea(repairman);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/chooseRepairman",method = RequestMethod.POST)
    @ResponseBody
    public void operatorChooseRepairman(@RequestBody FixOrder fixOrder){
        operatorService.chooseRepairman(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryComplainOrderByOrderStatus",method = RequestMethod.POST)
    @ResponseBody
    public List<ComplainOrder> operatorQueryComplainOrderByOrderStatus(@RequestBody ComplainOrder complainOrder){
        return operatorService.queryComplainOrderByOrderStatus(complainOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/updateOperatorResponse",method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateOperatorResponse(@RequestBody ComplainOrder complainOrder){
        operatorService.updateOperatorResponse(complainOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/updateRoomInformationByRoomId",method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateRoomInformationByRoomId(@RequestBody Room room){
        operatorService.updateRoomInformationByRoomId(room);
    }

    @RequestMapping(value = "/operator/updateOperatorInformationByOperator",method = RequestMethod.POST)
    @ResponseBody
    public void operatorUpdateOperatorInformationByOperator(@RequestBody Operator operator) throws Exception {
        operatorService.updateOperatorInformationByOperator(operator);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryOperatorByOperatorId",method = RequestMethod.POST)
    @ResponseBody
    public Operator operatorQueryOperatorByOperatorId(@RequestBody Operator operator){
        return operatorService.queryOperatorByOperatorId(operator);
    }

    @CrossOrigin
    @RequestMapping(value = "/operator/queryRoomByRoomId",method = RequestMethod.POST)
    @ResponseBody
    public Room queryRoomByRoomId(@RequestBody Room room){
        return operatorService.queryRoomByRoomId(room);
    }
    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////

    /**
     * 登录
     * @param repairman
     * @return “0”成功 “1”账号或密码错误 “2”账号不存在
     */
    @CrossOrigin
    @RequestMapping(value = "/repairman/login", method = RequestMethod.POST)
    @ResponseBody
    public String repairmanLogin(@RequestBody Repairman repairman) throws Exception {
        return repairmanService.login(repairman);
    }

    @CrossOrigin
    @RequestMapping(value = "/repairman/getId", method = RequestMethod.POST)
    @ResponseBody
    public String repairmanGetRepairmanByEmailAndPassWord(@RequestBody Repairman repairman){
        return repairmanService.getId(repairman).getRepairmanId();
    }

    @CrossOrigin
    @RequestMapping(value = "/repairman/queryAllFixOrder", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> repairmanQueryAllFixOrder(@RequestBody FixOrder fixOrder){
        return repairmanService.queryAllFixOrder(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/repairman/queryFixOrderByOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<FixOrder> repairmanQueryFixOrderByOrderStatus(@RequestBody FixOrder fixOrder){
        return repairmanService.queryFixOrderByOrderStatus(fixOrder);
    }

    @CrossOrigin
    @RequestMapping(value = "/repairman/updateOrderStatusByRepairmanId", method = RequestMethod.POST)
    @ResponseBody
    public void repairmanUpdateOrderStatusByRepairmanId(@RequestBody FixOrder fixOrder){
        repairmanService.updateOrderStatusByRepairmanId(fixOrder);
    }

    @RequestMapping(value = "/repairman/findFixOrderByFixOrderId", method = RequestMethod.POST)
    @ResponseBody
    public FixOrder repairmanFindFixOrderByFixOrderId(@RequestBody FixOrder fixOrder){
        return repairmanService.findFixOrderByFixOrderId(fixOrder);
    }

    ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////

    @PostMapping("/file/uploadImage")
    @ResponseBody
    public Result uploadImage(@RequestParam("picture") MultipartFile file){
        if(file.isEmpty())
            return new Result(false, "上传失败");;
        String fileName = file.getOriginalFilename();
        try {
            Picture uploadFile = new Picture();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(new Date());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());

            Picture savedFile = mongoTemplate.save(uploadFile);
            String url = "http://114.116.9.214:8000/api/v1/file/image/"+ savedFile.getId();
            return new Result(true,url);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }

    @GetMapping(value = "/file/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable String id) {
        byte[] data = null;
        Query query=new Query(Criteria.where("id").is(id));
        Picture file = mongoTemplate.findOne(query, Picture.class);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }

    @GetMapping(value = "/file/queryAll")
    @ResponseBody
    public List<Picture> queryAll() {
        Query query=new Query();
        List<Picture> pictures = mongoTemplate.find(query, Picture.class);
        return pictures;
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