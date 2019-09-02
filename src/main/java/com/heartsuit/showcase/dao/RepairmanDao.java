package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.util.AesEncryptUtils;
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
public class RepairmanDao {
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "RM";
    @Autowired
    public RepairmanDao(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public void insert(com.heartsuit.showcase.domain.Repairman repairman) throws Exception {
        Document document = new Document();
        document.put("repairmanId", StringUtil.convertNullToEmpty(UUID.randomUUID().toString().replace("-","")));
        document.put("name", StringUtil.convertNullToEmpty(repairman.getName()));
        document.put("age", StringUtil.convertNullToEmpty(repairman.getAge()));
        document.put("email", StringUtil.convertNullToEmpty(repairman.getEmail()));
        document.put("password", StringUtil.convertNullToEmpty(AesEncryptUtils.encrypt(repairman.getPassword())));
        document.put("sex", StringUtil.convertNullToEmpty(repairman.getSex()));
        document.put("area", StringUtil.convertNullToEmpty(repairman.getArea()));
        document.put("task", StringUtil.convertNullToEmpty("0"));
        document.put("telephone", StringUtil.convertNullToEmpty(repairman.getTelephone()));
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    public List<com.heartsuit.showcase.domain.Repairman> findAll(){
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<com.heartsuit.showcase.domain.Repairman> repairmen = new ArrayList<>();
        for (Document document : documents) {
            com.heartsuit.showcase.domain.Repairman newRepairman = new com.heartsuit.showcase.domain.Repairman();
            convertRepairman(document, newRepairman);
            repairmen.add(newRepairman);
        }
        return repairmen;
    }

    public List<com.heartsuit.showcase.domain.Repairman> findAllByArea(com.heartsuit.showcase.domain.Repairman repairman){
        Document targetDocument = new Document();
        targetDocument.put("area", repairman.getArea());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<com.heartsuit.showcase.domain.Repairman> repairmen = new ArrayList<>();
        for (Document document : documents) {
            com.heartsuit.showcase.domain.Repairman newRepairman = new com.heartsuit.showcase.domain.Repairman();
            convertRepairman(document, newRepairman);
            repairmen.add(newRepairman);
        }
        return repairmen;
    }

    public String findRepeatEmail(com.heartsuit.showcase.domain.Repairman repairman){
        Document document = new Document();
        document.put("email", repairman.getEmail());
        long documents = mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
        return documents > 0 ? "1":"0";
    }

    public com.heartsuit.showcase.domain.Repairman getRepairmanByEmail(com.heartsuit.showcase.domain.Repairman repairman){
        Document document = new Document();
        document.put("email", repairman.getEmail());
        Document targetDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        com.heartsuit.showcase.domain.Repairman targetRepairman = new com.heartsuit.showcase.domain.Repairman();
        convertRepairman(targetDocument, targetRepairman);
        return targetRepairman;
    }

    public long findRepairmanByEmail(com.heartsuit.showcase.domain.Repairman repairman) {
        Document document = new Document();
        document.put("email", repairman.getEmail());
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    public long findRepairmanByEmailAndPassWord(com.heartsuit.showcase.domain.Repairman repairman) throws Exception {
        Document document = new Document();
        document.put("email", repairman.getEmail());
        document.put("password", AesEncryptUtils.encrypt(repairman.getPassword()));
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    public void addTaskByRepairmanId(com.heartsuit.showcase.domain.Repairman repairman){
        Document document = new Document();
        document.put("repairmanId", repairman.getRepairmanId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            System.out.println("1");
            int task = Integer.valueOf(first.getString("task"));
            System.out.println("2");
            task++;
            System.out.println("3");
            first.put("task",String.valueOf(task));
            System.out.println("4");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void minusTaskByRepairmanId(com.heartsuit.showcase.domain.Repairman repairman){
        Document document = new Document();
        document.put("repairmanId", repairman.getRepairmanId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            int task = Integer.valueOf(first.getString("task"));
            task--;
            first.put("task",String.valueOf(task));
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public com.heartsuit.showcase.domain.Repairman createRepairmanByRepairmanId(String repairmanId){
        com.heartsuit.showcase.domain.Repairman repairman = new com.heartsuit.showcase.domain.Repairman();
        repairman.setRepairmanId(repairmanId);
        return repairman;
    }

    public void convertRepairman(Document document, com.heartsuit.showcase.domain.Repairman repairman) {
        repairman.setName(document.getString("name"));
        repairman.setAge(document.getString("age"));
        repairman.setEmail(document.getString("email"));
        repairman.setPassword(document.getString("password"));
        repairman.setSex(document.getString("sex"));
        repairman.setTask(document.getString("task"));
        repairman.setTelephone(document.getString("telephone"));
        repairman.setRepairmanId(document.getString("repairmanId"));
        repairman.setArea(document.getString("area"));
    }
}
