package com.heartsuit.showcase.domain;

public class ComplainOrder {
    private String complainOrderId;
    private String tenantId;
    private String tenantName;
    private String roomId;
    private String roomAddress;
    private String roomName;
    private String rentOrderId;
    private String picture;
    private String description;
    private String orderStatus;
    private String operatorResponse;

    public String getComplainOrderId() {
        return complainOrderId;
    }

    public void setComplainOrderId(String complainOrderId) {
        this.complainOrderId = complainOrderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRentOrderId() {
        return rentOrderId;
    }

    public void setRentOrderId(String rentOrderId) {
        this.rentOrderId = rentOrderId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOperatorResponse() {
        return operatorResponse;
    }

    public void setOperatorResponse(String operatorResponse) {
        this.operatorResponse = operatorResponse;
    }

    @Override
    public String toString() {
        return "ComplainOrder{" +
                "complainOrderId='" + complainOrderId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomAddress='" + roomAddress + '\'' +
                ", roomName='" + roomName + '\'' +
                ", rentOrderId='" + rentOrderId + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", operatorResponse='" + operatorResponse + '\'' +
                '}';
    }
}
