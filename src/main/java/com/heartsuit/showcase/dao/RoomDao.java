package com.heartsuit.showcase.dao;


import com.heartsuit.showcase.domain.Room;
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
public class RoomDao {
    private static final String COLLECTION_NAME = "Room";
    private MongoTemplate mongoTemplate;

    @Autowired
    public RoomDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 插入房间信息
     * @param room
     */
    public void insert(Room room){
        Document document = new Document();
        document.put("roomId", UUID.randomUUID().toString().replace("-",""));
        document.put("name", StringUtil.convertNullToEmpty(room.getName()));
        document.put("type", StringUtil.convertNullToEmpty(room.getType()));
        document.put("price", StringUtil.convertNullToEmpty(room.getPrice()));
        document.put("address",StringUtil.convertNullToEmpty(room.getAddress()));
        document.put("description",StringUtil.convertNullToEmpty(room.getDescription()));
        document.put("isAvailable","0");
        document.put("isAbandoned","0");
        document.put("picture",room.getPicture());
        mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
    }

    /**
     * 查找所有房源信息
     * @return room的list 所有房源信息
     */
    public List<Room> operatorFindAll() {
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find();
        ArrayList<Room> rooms = new ArrayList<>();
        for (Document document : documents) {
            Room room = convertRoom(document);
            rooms.add(room);
        }
        return rooms;
    }
    /**
     * 用户查找可用房源信息
     * @return room的list 所有房源信息
     */
    public List<Room> tenantFindAll(Room room) {
        Document tenantIdDocument = new Document(); //创建一个document对象
        tenantIdDocument.put("isAvailable", room.getIsAvailable()); //给document设置isAvailable属性
        tenantIdDocument.put("isAbandoned", room.getIsAbandoned());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(tenantIdDocument); //根据具有tenantId属性的document对象进行查询
        List<Room> Rooms = new ArrayList<>();
        for (Document document : documents) {
            Room resultRoom= convertRoom(document); //将查询出来的结果转换为java建模
            Rooms.add(resultRoom);
        }
        return Rooms; //返回查询的所有Room对象
    }
//    //修改房间信息 二次迭代再补充
//     public void updateIsAbandoned(Room room) {
//        Document document = new Document();
//        document.put("IsAbandoned",room.);
//        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
//        Document first = documents.first();
//        if (null != first) {
//            first.put("isActivation", "1");
//            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
//        }
//    }

    public void updateRoomNotAvailable(Room room) {
        Document document = new Document();
        document.put("roomId", room.getRoomId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("isAvailable", "1");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void updateRoomAvailable(Room room) {
        Document document = new Document();
        document.put("roomId", room.getRoomId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("isAvailable", "0");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    public void updateRoomInformation(Room room) {
        Document document = new Document();
        document.put("roomId", room.getRoomId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (null != first) {
            first.put("name", room.getName());
            first.put("price", room.getPrice());
            first.put("address", room.getAddress());
            first.put("description", room.getDescription());
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }

    /**
     * 根据房间类型查找房源信息
     * @param room
     * @return
     */
    public List<Room> findRoomByType(Room room) {
        Document typeDocument = new Document(); //创建一个document对象
        typeDocument.put("type", room.getType()); //给document设置type属性
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(typeDocument); //根据具有type属性的document对象进行查询
        List<Room> rooms = new ArrayList<>();
        for (Document document : documents) {
            Room resultRoom = convertRoom(document); //将查询出来的结果转换为java建模
            rooms.add(resultRoom);
        }
        return rooms; //返回查询的所有Room对象
    }

    public Room findRoomByRoomId(Room room) {
        Document document = new Document();
        document.put("roomId", room.getRoomId());
        Document findDocument = mongoTemplate.getCollection(COLLECTION_NAME).find(document).first();
        if (findDocument != null) {
            return convertRoom(findDocument);
        }
        return new Room();
    }
    public void reSetIsAbandoned(Room room){
        Room newRoom =new Room();
        Document document = new Document();
        document.put("roomId", room.getRoomId());
        FindIterable<Document> documents = mongoTemplate.getCollection(COLLECTION_NAME).find(document);
        Document first = documents.first();
        if (first != null) {
            newRoom = convertRoom(first);
            if(newRoom.getIsAbandoned().equals("0"))
                first.put("isAbandoned", "1");
            else
                first.put("isAbandoned", "0");
            mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(document, first);
        }
    }
    private Room convertRoom(Document document) {
        Room room = new Room();
        room.setName(document.getString("name"));
        room.setPrice(document.getString("price"));
        room.setRoomId(document.getString("roomId"));
        room.setType(document.getString("type"));
        room.setAddress(document.getString("address"));
        room.setIsAbandoned(document.getString("isAbandoned"));
        room.setDescription(document.getString("description"));
        room.setIsAvailable(document.getString("isAvailable"));
        room.setPicture(document.getString("picture"));
        return room;
    }
}
