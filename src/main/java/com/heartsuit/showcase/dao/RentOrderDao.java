package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.util.StringUtil;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.heartsuit.showcase.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RentOrderDao {
    private DateUtil dateUtil = new DateUtil();
    private static final String COLLECTION_NAME = "RO";
    private MongoTemplate mongoTemplate;
    @Autowired
    public RentOrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * 插入订单信息
     * @param rentOrder
     */
    public void insert(RentOrder rentOrder){
        Document document = new Document();
        document.put("orderId", UUID.randomUUID().toString().replace("-",""));
        document.put("tenantId", StringUtil.convertNullToEmpty(rentOrder.getTenantId()));
        document.put("roomId", StringUtil.convertNullToEmpty(rentOrder.getRoomId()));
        document.put("rentType", StringUtil.convertNullToEmpty(rentOrder.getRentType()));
        document.put("startDate", dateUtil.getCurrentDate());
        if(rentOrder.getRentType().equals("长期"))
            document.put("endDate", dateUtil.addMonth(dateUtil.getCurrentDate(),rentOrder.getRentTime()));
        else
            document.put("endDate", dateUtil.addDay(dateUtil.getCurrentDate(),rentOrder.getRentTime()));
        document.put("rentTime", StringUtil.convertNullToEmpty(rentOrder.getRentTime()));
        document.put("rentMoney", StringUtil.convertNullToEmpty(rentOrder.getRentMoney()));
        document.put("orderStatus", "未审核");
        document.put("isAbandoned", "0");
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }
    /**
     * 查找所有订单信息
     * @return rentorder的list 所有订单信息
     */
    public List<RentOrder> findAll() {
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<RentOrder> rooms = new ArrayList<>();
        for (Document document : documents) {
            RentOrder rentOrder = convertRentOrder(document);
            rooms.add(rentOrder);
        }
        return rooms;
    }

    /**
     * 客服根据用户ID查找订单信息
     * @param rentOrder
     * @return
     */
    public List<RentOrder> OperatorFindRentOrderByTenantId(RentOrder rentOrder) {
        Document tenantIdDocument = new Document(); //创建一个document对象
        tenantIdDocument.put("tenantId", rentOrder.getTenantId()); //给document设置tenantId属性
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(tenantIdDocument); //根据具有tenantId属性的document对象进行查询
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document : documents) {
            RentOrder resultRentOrder= convertRentOrder(document); //将查询出来的结果转换为java建模
            RentOrders.add(resultRentOrder);
        }
        return RentOrders; //返回查询的所有Room对象
    }

    public List<RentOrder> findRentOrderByOrderStatus(RentOrder rentOrder) {
        Document targetDocument = new Document(); //创建一个document对象
        targetDocument.put("orderStatus", rentOrder.getOrderStatus()); //给document设置orderStatus属性
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument); //根据具有orderStatus属性的document对象进行查询
        List<RentOrder> rentOrders = new ArrayList<>();
        for (Document document : documents) {
            RentOrder resultRentOrder= convertRentOrder(document); //将查询出来的结果转换为java建模
            rentOrders.add(resultRentOrder);
        }
        return rentOrders; //返回查询的所有rentOrder对象
    }

    public RentOrder getRentOrderByOrderId(RentOrder rentOrder) {
        Document document = new Document();
        document.put("orderId", rentOrder.getOrderId());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        RentOrder findRentOrder = new RentOrder();
        findRentOrder = convertRentOrder(findDocument);
        return findRentOrder;
    }

    public List<RentOrder> getRentOrderNotPaidAndRentByMonth(){
        Document document = new Document();
        document.put("rentType", "长期");
        document.put("orderStatus", "未支付");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> targetRentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            targetRentOrders.add(resultRentOrder);
        }
        return targetRentOrders;
    }

    public List<RentOrder> getRentOrderListByOrderId(RentOrder rentOrder) {
        Document document = new Document();
        document.put("orderId", rentOrder.getOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> targetRentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            targetRentOrders.add(resultRentOrder);
        }
        return targetRentOrders;
    }

    public void updateOrderStatusByOrderId(RentOrder rentOrder) {
        Document document = new Document();
        document.put("orderId", rentOrder.getOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("orderStatus", rentOrder.getOrderStatus());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public List<RentOrder> findLongLeaseWithoutPayRentOrder(){
        Document document = new Document();
        document.put("rentType","长期");
        document.put("orderStatus", "未支付");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            RentOrders.add(resultRentOrder);
        }
        return RentOrders;
    }

    public List<RentOrder> getRentOrderFinishAndRentByMonth(){
        Document document = new Document();
        document.put("rentType","长期");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            if(dateUtil.isLaterThanToday(resultRentOrder.getEndDate()).equals("before"))
                RentOrders.add(resultRentOrder);
        }
        return RentOrders;
    }

    public List<RentOrder> getRentOrderNotPaidAndRentByDay(){
        Document document = new Document();
        document.put("rentType","短期");
        document.put("orderStatus", "未支付");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            RentOrders.add(resultRentOrder);
        }
        return RentOrders;
    }

    public List<RentOrder> getRentOrderFinishAndRentByDay(){
        Document document = new Document();
        document.put("rentType","短期");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document1 : documents) {
            RentOrder resultRentOrder= convertRentOrder(document1); //将查询出来的结果转换为java建模
            if(dateUtil.isLaterThanToday(resultRentOrder.getEndDate()).equals("before"))
                RentOrders.add(resultRentOrder);
        }
        return RentOrders;
    }


    public void reRentUpdateEndDateAndRentTime(RentOrder rentOrder){
        Document document = new Document();
        document.put("orderId",rentOrder.getOrderId());
        document.put("rentType","长期");
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        RentOrder findRentOrder = new RentOrder();
        findRentOrder = convertRentOrder(findDocument);
        findDocument.put("rentTime",String.valueOf(Long.parseLong(rentOrder.getRentTime())+Long.parseLong(findRentOrder.getRentTime())));
        findDocument.put("endDate",dateUtil.addMonth(findRentOrder.getStartDate(),String.valueOf(Long.parseLong(rentOrder.getRentTime())+Long.parseLong(findRentOrder.getRentTime()))));
        mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document,findDocument);
    }
    /**
     * 生成合同
     * @param rentOrder
     * @return
     */
    public Contract createContractByRentOrderId(RentOrder rentOrder) {
        Document document = new Document();
        document.put("orderId", rentOrder.getOrderId());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Contract contract = new Contract();
        if (findDocument == null) {
            return contract;
        }
        RentOrder findRentOrder = convertRentOrder(findDocument);
        contract.setTenantId(findRentOrder.getTenantId());
        contract.setRoomId(findRentOrder.getRoomId());
        contract.setRentType(findRentOrder.getRentType());
        contract.setStartDate(findRentOrder.getStartDate());
        contract.setEndDate(findRentOrder.getEndDate());
        contract.setRentTime(findRentOrder.getRentTime());
        contract.setRentMoney(findRentOrder.getRentMoney());
        return contract;
    }

    /**
     * 本地删除订单记录
     * @param rentOrder
     */
    public void AbandonRentOrder(RentOrder rentOrder){
        Document document = new Document();
        document.put("orderId", rentOrder.getOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("isAbandoned", "1");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }
    /**
     * 本地显示订单记录
     * @param rentOrder
     * @return
     */
    public List<RentOrder> tenantFindRentOrderByTenantId(RentOrder rentOrder) {
        Document tenantIdDocument = new Document(); //创建一个document对象
        tenantIdDocument.put("tenantId", rentOrder.getTenantId()); //给document设置tenantId属性
        tenantIdDocument.put("isAbandoned","0");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(tenantIdDocument); //根据具有tenantId属性的document对象进行查询
        List<RentOrder> RentOrders = new ArrayList<>();
        for (Document document : documents) {
            RentOrder resultRentOrder= convertRentOrder(document); //将查询出来的结果转换为java建模
            RentOrders.add(resultRentOrder);
        }
        return RentOrders; //返回查询的所有Room对象
    }
    /**
     * 转换json格式为RentOrder
     * @param document
     * @return
     */
    private RentOrder convertRentOrder(Document document) {
        RentOrder rentOrder = new RentOrder();
        rentOrder.setOrderId(document.getString("orderId"));
        rentOrder.setTenantId(document.getString("tenantId"));
        rentOrder.setRoomId(document.getString("roomId"));
        rentOrder.setRentType(document.getString("rentType"));
        rentOrder.setStartDate(document.getString("startDate"));
        rentOrder.setEndDate(document.getString("endDate"));
        rentOrder.setRentTime(document.getString("rentTime"));
        rentOrder.setRentMoney(document.getString("rentMoney"));
        rentOrder.setOrderStatus(document.getString("orderStatus"));
        rentOrder.setIsAbandoned(document.getString("isAbandoned"));
        return rentOrder;
    }
}
