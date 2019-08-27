package com.heartsuit.showcase.dao;

import com.heartsuit.showcase.domain.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class OperatorDao {
    private MongoTemplate mongoTemplate;

    private static final String collectionName = "OA";
    @Autowired
    public OperatorDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
