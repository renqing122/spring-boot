package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.Operator;
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
public class OperatorDao {
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "OA";
    @Autowired
    public OperatorDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 插入客服信息
     * @param operator
     */
    public void insert(Operator operator) throws Exception {
        Document document = new Document();
        document.put("operatorId", UUID.randomUUID().toString().replace("-",""));
        document.put("operatorName", StringUtil.convertNullToEmpty(operator.getOperatorName()));
        document.put("email", StringUtil.convertNullToEmpty(operator.getEmail()));
        document.put("password", StringUtil.convertNullToEmpty( AesEncryptUtils.encrypt(operator.getPassword())));
        document.put("age", StringUtil.convertNullToEmpty(operator.getAge()));
        document.put("sex", StringUtil.convertNullToEmpty(operator.getSex()));
        document.put("telephone", StringUtil.convertNullToEmpty(operator.getTelephone()));
        document.put("isActivation", StringUtil.convertNullToEmpty("0"));
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    /**
     * 查找所有客服信息
     * @return 所有客服信息
     */
    public List<Operator> findAll() {
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<Operator> operators = new ArrayList<>();
        for (Document document : documents) {
            Operator operator = new Operator();
            convertOperator(document, operator);
            operators.add(operator);
        }
        return operators;
    }

    /**
     * 激活客服账号
     * @param operator
     */
    public void updateActivationStatus(Operator operator) {
        Document document = new Document();
        document.put("email", operator.getEmail());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("isActivation", operator.getIsActivation());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    /**
     * 查找是否有重复邮箱
     * @param operator
     * @return “1”有重复邮箱 “0”没有重复邮箱
     */
    public String findRepeatEmail(Operator operator) {
        Document document = new Document();
        document.put("email", operator.getEmail());
        long documents = mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
        return documents > 0 ? "1":"0";
    }

    public long findOperatorByEmail(Operator operator) {
        Document document = new Document();
        document.put("email", operator.getEmail());
        document.put("isActivation","1");
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    /**
     * 检验登录信息
     * @param operator
     * @return 匹配上的账号条数
     */
    public long findOperatorByEmailAndPassWord(Operator operator) throws Exception {
        Document document = new Document();
        document.put("email", operator.getEmail());
        document.put("password", AesEncryptUtils.encrypt(operator.getPassword()));
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    public Operator getOperatorByEmail(Operator operator){
        Document document = new Document();
        document.put("email", operator.getEmail());
        Document targetDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Operator targetOperator = new Operator();
        convertOperator(targetDocument, targetOperator);
        return targetOperator;
    }

    public Operator getOperatorByOperatorId(Operator operator){
        Document document = new Document();
        document.put("operatorId", operator.getOperatorId());
        Document targetDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Operator targetOperator = new Operator();
        convertOperator(targetDocument, targetOperator);
        return targetOperator;
    }
    public List<Operator> findAllOperatorByActivationStatus0() {
        Document targetDocument = new Document();
        targetDocument.put("isActivation", "0");
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(targetDocument);
        ArrayList<Operator> operators = new ArrayList<>();
        for (Document document : documents) {
            Operator operator = new Operator();
            convertOperator(document, operator);
            operators.add(operator);
        }
        return operators;
    }

    public void updateOperatorInformation(Operator operator) throws Exception {
        Document document = new Document();
        document.put("operatorId", operator.getOperatorId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("operatorName", operator.getOperatorName());
            first.put("password", AesEncryptUtils.encrypt(operator.getPassword()));
            first.put("age", operator.getAge());
            first.put("sex", operator.getSex());
            first.put("telephone", operator.getTelephone());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    /**
     * 将document的数据放到operator里
     * @param document
     * @param operator
     */
    public void convertOperator(Document document, Operator operator) {
        operator.setOperatorName(document.getString("operatorName"));
        operator.setAge(document.getString("age"));
        operator.setSex(document.getString("sex"));
        operator.setOperatorId(document.getString("operatorId"));
        operator.setEmail(document.getString("email"));
        operator.setPassword(document.getString("password"));
        operator.setTelephone(document.getString("telephone"));
        operator.setIsActivation(document.getString("isActivation"));
    }
}
