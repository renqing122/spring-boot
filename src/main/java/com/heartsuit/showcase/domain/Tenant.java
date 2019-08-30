package com.heartsuit.showcase.domain;

/**
 * Created by Administrator on 2019/8/24 0024 17:25
 * 租客
 */
public class Tenant {
    private String email; // 邮箱
    private String password; // 密码
    private String tenantName; // 用户名
    private String age; // 年龄
    private String sex; // 性别
    private String telephone; // 手机号
    private String isActivation; // 是否激活
    private String code; // 激活码
    private String tenantId; // 用户ID
    private String level; // 等级

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
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

    public String getIsActivation() {
        return isActivation;
    }

    public void setIsActivation(String isActivation) {
        this.isActivation = isActivation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone='" + telephone + '\'' +
                ", isActivation='" + isActivation + '\'' +
                ", code='" + code + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
