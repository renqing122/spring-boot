package com.heartsuit.showcase.domain;

public class Room {
    private String roomId; //房间ID
    private String name; //房间号
    private String type; //房间类型 几人间
    private String price; //单位房间的租金
    private String address; //房间地址
    private String description; //简介
    private String isAvailable; //是否被占用
    private String isAbandoned; //是否被禁用
    private String picture;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsAbandoned() {
        return isAbandoned;
    }

    public void setIsAbandoned(String isAbandoned) {
        this.isAbandoned = isAbandoned;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                ", isAbandoned='" + isAbandoned + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
