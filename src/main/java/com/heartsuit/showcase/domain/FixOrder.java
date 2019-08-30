package com.heartsuit.showcase.domain;

public class FixOrder {
    private String fixOrderId;
    private String tenantId;
    private String roomId;
    private String rentOrderId;
    private String orderTime;
    private String area;
    private String picture;
    private String description;
    private String repairmanId;
    private String repairmanName;
    private String orderStatus;
    private String tenantComment;

    public String getFixOrderId() {
        return fixOrderId;
    }

    public void setFixOrderId(String fixOrderId) {
        this.fixOrderId = fixOrderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRentOrderId() {
        return rentOrderId;
    }

    public void setRentOrderId(String rentOrderId) {
        this.rentOrderId = rentOrderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepairmanId() {
        return repairmanId;
    }

    public void setRepairmanId(String repairmanId) {
        this.repairmanId = repairmanId;
    }

    public String getRepairmanName() {
        return repairmanName;
    }

    public void setRepairmanName(String repairmanName) {
        this.repairmanName = repairmanName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTenantComment() {
        return tenantComment;
    }

    public void setTenantComment(String tenantComment) {
        this.tenantComment = tenantComment;
    }

    @Override
    public String toString() {
        return "FixOrder{" +
                "fixOrderId='" + fixOrderId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", rentOrderId='" + rentOrderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", area='" + area + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", repairmanId='" + repairmanId + '\'' +
                ", repairmanName='" + repairmanName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", tenantComment='" + tenantComment + '\'' +
                '}';
    }
}
