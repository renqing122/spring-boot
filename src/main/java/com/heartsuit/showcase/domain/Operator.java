package com.heartsuit.showcase.domain;

public class Operator {
    private String email; // 邮箱
    private String password; // 密码
    private String operatorName; // 姓名
    private String age; // 年龄
    private String sex; // 性别
    private String telephone; // 手机号
    private String isActivation; // 是否激活
    private String operatorId; //客服ID

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIsActivation() {
        return isActivation;
    }

    public void setIsActivation(String isActivation) {
        this.isActivation = isActivation;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone='" + telephone + '\'' +
                ", isActivation='" + isActivation + '\'' +
                ", operatorId='" + operatorId + '\'' +
                '}';
    }
}
