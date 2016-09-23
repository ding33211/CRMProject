package com.soubu.crmproject.base.greendao;

import com.soubu.crmproject.model.ContactParams;

import org.greenrobot.greendao.annotation.*;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "Contact".
 */
@Entity(nameInDb = "Contact")
public class Contact implements Serializable{

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String customer;
    private String position;
    private String phone;
    private String mobile;
    private String qq;
    private String wechat;
    private String wangwang;
    private String department;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
    private java.util.Date touchedAt;
    private String touchedCount;
    private String contact_id;

    @Generated
    public Contact() {
    }

    public Contact(Long id) {
        this.id = id;
    }

    @Generated
    public Contact(Long id, String name, String customer, String position, String phone, String mobile, String qq, String wechat, String wangwang, String department, java.util.Date createdAt, java.util.Date updatedAt, java.util.Date touchedAt, String touchedCount, String contact_id) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.position = position;
        this.phone = phone;
        this.mobile = mobile;
        this.qq = qq;
        this.wechat = wechat;
        this.wangwang = wangwang;
        this.department = department;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.touchedAt = touchedAt;
        this.touchedCount = touchedCount;
        this.contact_id = contact_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public java.util.Date getTouchedAt() {
        return touchedAt;
    }

    public void setTouchedAt(java.util.Date touchedAt) {
        this.touchedAt = touchedAt;
    }

    public String getTouchedCount() {
        return touchedCount;
    }

    public void setTouchedCount(String touchedCount) {
        this.touchedCount = touchedCount;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public ContactParams copyToContactParams(){
        ContactParams params = new ContactParams();
        params.setMobile(mobile);
        params.setCreatedAt(createdAt);
        params.setCustomerId(customer);
        params.setDepartment(department);
        params.setName(name);
        params.setPhone(phone);
        params.setPosition(position);
        params.setQq(qq);
        params.setUpdatedAt(updatedAt);
        params.setWangwang(wangwang);
        params.setWechat(wechat);
        params.setTouchedAt(touchedAt);
        params.setTouchedCount(touchedCount);
        params.setId(contact_id);
        return params;
    }

}
