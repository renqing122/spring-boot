package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.SystemMaster;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class SystemMasterDao {
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "SM";
    @Autowired
    public SystemMasterDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 将document的数据放到operator里
     * @param document
     * @param systemMaster
     */
    public void convertOperator(Document document, SystemMaster systemMaster) {
        systemMaster.setAccount(document.getString("account"));
        systemMaster.setPassword(document.getString("password"));
    }
}
