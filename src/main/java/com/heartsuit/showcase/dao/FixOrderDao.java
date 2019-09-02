package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.FixOrder;
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
public class FixOrderDao {
    private MongoTemplate mongoTemplate;
    private static final String COLLECTION_NAME = "FO";
    @Autowired
    public FixOrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(FixOrder fixOrder){
        org.bson.Document document = new Document();
        document.put("fixOrderId", StringUtil.convertNullToEmpty(UUID.randomUUID().toString().replace("-","")));
        document.put("tenantId", StringUtil.convertNullToEmpty(fixOrder.getTenantId()));
        document.put("roomId", StringUtil.convertNullToEmpty(fixOrder.getRoomId()));
        document.put("rentOrderId", StringUtil.convertNullToEmpty(fixOrder.getRentOrderId()));
        document.put("orderTime", StringUtil.convertNullToEmpty(fixOrder.getOrderTime()));
        document.put("area", StringUtil.convertNullToEmpty(fixOrder.getArea()));
        document.put("picture", StringUtil.convertNullToEmpty(fixOrder.getPicture()));
        document.put("description", StringUtil.convertNullToEmpty(fixOrder.getDescription()));
        document.put("repairmanId", StringUtil.convertNullToEmpty(fixOrder.getRepairmanId()));
        document.put("repairmanName", StringUtil.convertNullToEmpty(fixOrder.getRepairmanName()));
        document.put("orderStatus", StringUtil.convertNullToEmpty(fixOrder.getOrderStatus()));
        document.put("tenantComment", StringUtil.convertNullToEmpty(fixOrder.getTenantComment()));
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    public List<FixOrder> findAll(){
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<FixOrder> fixOrders = new ArrayList<>();
        for (Document document : documents) {
            FixOrder fixOrder = new FixOrder();
            convertFixOrder(document, fixOrder);
            fixOrders.add(fixOrder);
        }
        return fixOrders;
    }

    public List<FixOrder> findFixOrderByOrderStatus(FixOrder fixOrder){
        Document targetDocument = new Document();
        targetDocument.put("orderStatus", fixOrder.getOrderStatus());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<FixOrder> fixOrders = new ArrayList<>();
        for (Document document : documents) {
            FixOrder newFixOrder = new FixOrder();
            convertFixOrder(document, newFixOrder);
            fixOrders.add(newFixOrder);
        }
        return fixOrders;
    }

    public FixOrder findFixOrderByFixOrderId(FixOrder fixOrder){
        Document targetDocument = new Document();
        targetDocument.put("fixOrderId", fixOrder.getFixOrderId());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument).first();
        FixOrder findFixOrder = new FixOrder();
        convertFixOrder(findDocument,findFixOrder);
        return findFixOrder;
    }

    public List<FixOrder> findFixOrderByTenantId(FixOrder fixOrder){
        Document targetDocument = new Document();
        targetDocument.put("tenantId", fixOrder.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<FixOrder> fixOrders = new ArrayList<>();
        for (Document document : documents) {
            FixOrder newFixOrder = new FixOrder();
            convertFixOrder(document, newFixOrder);
            fixOrders.add(newFixOrder);
        }
        return fixOrders;
    }

    public List<FixOrder> findFixOrderByRepairmanId(FixOrder fixOrder){
        Document targetDocument = new Document();
        targetDocument.put("repairmanId", fixOrder.getRepairmanId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<FixOrder> fixOrders = new ArrayList<>();
        for (Document document : documents) {
            FixOrder newFixOrder = new FixOrder();
            convertFixOrder(document, newFixOrder);
            fixOrders.add(newFixOrder);
        }
        return fixOrders;
    }

    public List<FixOrder> findFixOrderByRepairmanIdAndOrderStatus(FixOrder fixOrder){
        Document targetDocument = new Document();
        targetDocument.put("repairmanId", fixOrder.getRepairmanId());
        targetDocument.put("orderStatus", fixOrder.getOrderStatus());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<FixOrder> fixOrders = new ArrayList<>();
        for (Document document : documents) {
            FixOrder newFixOrder = new FixOrder();
            convertFixOrder(document, newFixOrder);
            fixOrders.add(newFixOrder);
        }
        return fixOrders;
    }

    public FixOrder createFixOrderByRepairmanIdAndOrderStatus(String repairmanId){
        FixOrder fixOrder = new FixOrder();
        fixOrder.setRepairmanId(repairmanId);
        return fixOrder;
    }

    public void updateRepairmanIdByFixOrderId(FixOrder fixOrder){
        Document document = new Document();
        document.put("fixOrderId", fixOrder.getFixOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("repairmanId", fixOrder.getRepairmanId());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void updateOrderStatusByFixOrderId(FixOrder fixOrder){
        Document document = new Document();
        document.put("fixOrderId", fixOrder.getFixOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("orderStatus", fixOrder.getOrderStatus());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void updateTenantCommentByFixOrderId(FixOrder fixOrder){
        Document document = new Document();
        document.put("fixOrderId", fixOrder.getFixOrderId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("tenantComment", fixOrder.getTenantComment());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void convertFixOrder(Document document, FixOrder fixOrder) {
        fixOrder.setFixOrderId(document.getString("fixOrderId"));
        fixOrder.setTenantId(document.getString("tenantId"));
        fixOrder.setRoomId(document.getString("roomId"));
        fixOrder.setRentOrderId(document.getString("rentOrderId"));
        fixOrder.setOrderTime(document.getString("orderTime"));
        fixOrder.setArea(document.getString("area"));
        fixOrder.setPicture(document.getString("picture"));
        fixOrder.setDescription(document.getString("description"));
        fixOrder.setRepairmanId(document.getString("repairmanId"));
        fixOrder.setRepairmanName(document.getString("repairmanName"));
        fixOrder.setOrderStatus(document.getString("orderStatus"));
        fixOrder.setTenantComment(document.getString("tenantComment"));
    }
}
