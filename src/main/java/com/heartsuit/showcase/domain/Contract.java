package com.heartsuit.showcase.domain;

public class Contract {
    private String tenantId; //用户ID
    private String roomId; //房间ID
    private String rentType; //租赁类型 短租为"短期租赁" 长租为"长期租赁"
    private String startDate; //租赁开始时间
    private String endDate; //租赁结束时间
    private String rentTime; //租赁时长 短租单位为天 长租单位为月
    private String rentMoney; //租赁总租金

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

    @Override
    public String toString() {
        return "Contract{" +
                "tenantId='" + tenantId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", rentType='" + rentType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", rentTime='" + rentTime + '\'' +
                ", rentMoney='" + rentMoney + '\'' +
                '}';
    }
}
