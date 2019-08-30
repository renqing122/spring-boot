package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.Contract;
import com.heartsuit.showcase.domain.RentOrder;
import com.heartsuit.showcase.domain.Room;
import com.heartsuit.showcase.util.StringUtil;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RentOrderDao {
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
        document.put("startDate", StringUtil.convertNullToEmpty(rentOrder.getStartDate()));
        document.put("endDate", StringUtil.convertNullToEmpty(rentOrder.getEndDate()));
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
        contract.setRentType(convertString(findRentOrder.getRentType()));
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

    private String convertString(String string){
        if (string == "1")
            return "短期租赁";
        else
            return "长期租赁";
    }

}
