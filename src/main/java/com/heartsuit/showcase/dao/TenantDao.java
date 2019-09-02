package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.util.AesEncryptUtils;
import com.mongodb.client.FindIterable;
import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.util.StringUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2019/8/24 0024 19:44
 */
@Component
public class TenantDao
{
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "UA";
    @Autowired
    public TenantDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public void insert(Tenant tenant) throws Exception {
        Document document = new Document();
        String tenantId=UUID.randomUUID().toString().replace("-","");
        document.put("tenantId", StringUtil.convertNullToEmpty(tenantId));
        document.put("tenantName", StringUtil.convertNullToEmpty(tenant.getTenantName()));
        document.put("age", StringUtil.convertNullToEmpty(tenant.getAge()));
        document.put("email", StringUtil.convertNullToEmpty(tenant.getEmail()));
        document.put("password", StringUtil.convertNullToEmpty(AesEncryptUtils.encrypt(tenant.getPassword())));
        document.put("sex", StringUtil.convertNullToEmpty(tenant.getSex()));
        document.put("code", StringUtil.convertNullToEmpty(tenantId+tenantId));
        document.put("isActivation", StringUtil.convertNullToEmpty(tenant.getIsActivation()));
        document.put("telephone", StringUtil.convertNullToEmpty(tenant.getTelephone()));
        document.put("level","5");
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    public List<Tenant> findAll() {
            FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
            ArrayList<Tenant> tenants = new ArrayList<>();
            for (Document document : documents) {
                Tenant tenant = new Tenant();
                convertTenant(document, tenant);
                tenants.add(tenant);
            }
            return tenants;
    }

    public String checkCode(Tenant tenant){
        Document document = new Document();
        document.put("code", tenant.getCode());
        long documents = mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
        return documents > 0 ? "1":"0";
    }
    public void updateActivationStatus(Tenant tenant) {
        Document document = new Document();
        document.put("code", tenant.getCode());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("isActivation", "1");
            first.put("code","");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public String findRepeatEmail(Tenant tenant) {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        long documents = mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
        return documents > 0 ? "1":"0";
    }

    public long findTenantByEmail(Tenant tenant) {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        document.put("isActivation","1");
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    public Tenant findCodeByEmail(Tenant tenant) {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Tenant findTenant = new Tenant();
        convertTenant(findDocument, findTenant);
        return findTenant;
    }

    public Tenant findEmailByCode(Tenant tenant) {
        Document document = new Document();
        document.put("code", tenant.getCode());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Tenant findTenant = new Tenant();
        convertTenant(findDocument, findTenant);
        return findTenant;
    }


    public Tenant getTenantByTenantId(Tenant tenant) {
        Document document = new Document();
        document.put("tenantId", tenant.getTenantId());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Tenant findTenant = new Tenant();
        convertTenant(findDocument, findTenant);
        return findTenant;
    }

    public List<Tenant> getTenantListByTenantId(Tenant tenant) {
        Document document = new Document();
        document.put("tenantId", tenant.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        List<Tenant> targetTenants = new ArrayList<>();
        for (Document document1 : documents) {
            Tenant resultTenant = new Tenant();
            convertTenant(document1,resultTenant); //将查询出来的结果转换为java建模
            targetTenants.add(resultTenant);
        }
        return targetTenants;
    }

    public Tenant getTenantByEmail(Tenant tenant) {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Tenant findTenant = new Tenant();
        convertTenant(findDocument, findTenant);
        return findTenant;
    }

    public String findTenantIdByEmail(Tenant tenant) {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        Tenant findTenant = new Tenant();
        if(findDocument!=null)
        {
            convertTenant(findDocument, findTenant);
            return findTenant.getTenantId();
        }
        else
            return null;
    }

    public long findTenantByEmailAndPassWord(Tenant tenant) throws Exception {
        Document document = new Document();
        document.put("email", tenant.getEmail());
        document.put("password", AesEncryptUtils.encrypt(tenant.getPassword()));
        return mongoTemplate.getCollection(COLLECTION_NAME).countDocuments(document);
    }

    public void updateTenantLevel(Tenant tenant) {
        Document document = new Document();
        document.put("tenantId", tenant.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("level", tenant.getLevel());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void reSetPasswordByTenantId(Tenant tenant) throws Exception {
        Document document = new Document();
        document.put("tenantId", tenant.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("password", AesEncryptUtils.encrypt("000000"));
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public Tenant createTenantByCode(String code){
        Tenant tenant = new Tenant();
        tenant.setCode(code);
        return tenant;
    }

    public Tenant createTenantByTenantId(String tenantId){
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId);
        return tenant;
    }

    public void updateTenantInformation(Tenant tenant) throws Exception {
        Document document = new Document();
        document.put("tenantId", tenant.getTenantId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("tenantName", tenant.getTenantName());
            first.put("password", AesEncryptUtils.encrypt(tenant.getPassword()));
            first.put("age", tenant.getAge());
            first.put("sex", tenant.getSex());
            first.put("telephone", tenant.getTelephone());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }


    public void convertTenant(Document document, Tenant tenant) {
        tenant.setTenantName(document.getString("tenantName"));
        tenant.setAge(document.getString("age"));
        tenant.setEmail(document.getString("email"));
        tenant.setPassword(document.getString("password"));
        tenant.setSex(document.getString("sex"));
        tenant.setIsActivation(document.getString("isActivation"));
        tenant.setTelephone(document.getString("telephone"));
        tenant.setTenantId(document.getString("tenantId"));
        tenant.setCode(document.getString("code"));
        tenant.setLevel(document.getString("level"));
    }
}
