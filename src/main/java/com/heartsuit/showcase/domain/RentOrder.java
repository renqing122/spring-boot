package com.heartsuit.showcase.domain;

public class RentOrder {
    private String orderId; //订单ID
    private String userId; //用户ID
    private String roomId; //房间ID
    private String rentType; //租赁类型 短租为1 长租为2
    private String startDate; //租赁开始时间
    private String endDate; //租赁结束时间
    private String rentTime; //租赁时长 短租单位为天 长租单位为月
    private String rentMoney; //租赁总租金
    private String orderStatus; //订单状态 0未审核 1未通过审核 2审核通过，未支付 3逾期未支付 4进行中 5已结束

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(String rentMoney) {
        this.rentMoney = rentMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "RentOrder{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", rentType='" + rentType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", rentTime='" + rentTime + '\'' +
                ", rentMoney='" + rentMoney + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
