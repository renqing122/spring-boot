package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.ComplainOrder;
import com.heartsuit.showcase.util.StringUtil;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ComplainOrderDao {
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "CO";
    @Autowired
    public ComplainOrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(ComplainOrder complainOrder){
        org.bson.Document document = new Document();
        document.put("complainOrderId", StringUtil.convertNullToEmpty(UUID.randomUUID().toString().replace("-","")));
        document.put("tenantId", StringUtil.convertNullToEmpty(complainOrder.getTenantId()));
        document.put("roomId", StringUtil.convertNullToEmpty(complainOrder.getRoomId()));
        document.put("rentOrderId", StringUtil.convertNullToEmpty(complainOrder.getRentOrderId()));
        document.put("tenantName", StringUtil.convertNullToEmpty(complainOrder.getTenantName()));
        document.put("roomName", StringUtil.convertNullToEmpty(complainOrder.getRoomName()));
        document.put("picture", StringUtil.convertNullToEmpty(complainOrder.getPicture()));
        document.put("description", StringUtil.convertNullToEmpty(complainOrder.getDescription()));
        document.put("roomAddress", StringUtil.convertNullToEmpty(complainOrder.getRoomAddress()));
        document.put("orderStatus", StringUtil.convertNullToEmpty(complainOrder.getOrderStatus()));
        document.put("operatorResponse", StringUtil.convertNullToEmpty(complainOrder.getOperatorResponse()));
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    public List<ComplainOrder> findComplainOrderByOrderStatus(ComplainOrder complainOrder){
        Document targetDocument = new Document();
        targetDocument.put("orderStatus", complainOrder.getOrderStatus());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<ComplainOrder> complainOrders = new ArrayList<>();
        for (Document document : documents) {
            ComplainOrder newFixOrder = new ComplainOrder();
            convertComplainOrder(document, newFixOrder);
            complainOrders.add(newFixOrder);
        }
        return complainOrders;
    }

    public List<ComplainOrder> findComplainOrderByTenantId(ComplainOrder complainOrder){
        Document targetDocument = new Document();
        targetDocument.put("tenantId", complainOrder.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<ComplainOrder> complainOrders = new ArrayList<>();
        for (Document document : documents) {
            ComplainOrder targetComplainOrder = new ComplainOrder();
            convertComplainOrder(document, targetComplainOrder);
            complainOrders.add(targetComplainOrder);
        }
        return complainOrders;
    }

    public void updateOrderStatusAndOperatorResponseByComplainOrderId(ComplainOrder complainOrder){
        Document document = new Document();
        document.put("complainOrderId", complainOrder.getComplainOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("orderStatus", complainOrder.getOrderStatus());
            first.put("operatorResponse", complainOrder.getOperatorResponse());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }
    public void convertComplainOrder(Document document, ComplainOrder complainOrder) {
        complainOrder.setComplainOrderId(document.getString("complainOrderId"));
        complainOrder.setTenantId(document.getString("tenantId"));
        complainOrder.setRoomId(document.getString("roomId"));
        complainOrder.setRentOrderId(document.getString("rentOrderId"));
        complainOrder.setTenantName(document.getString("tenantName"));
        complainOrder.setRoomName(document.getString("roomName"));
        complainOrder.setPicture(document.getString("picture"));
        complainOrder.setDescription(document.getString("description"));
        complainOrder.setRoomAddress(document.getString("roomAddress"));
        complainOrder.setOrderStatus(document.getString("orderStatus"));
        complainOrder.setOperatorResponse(document.getString("operatorResponse"));
    }

}
