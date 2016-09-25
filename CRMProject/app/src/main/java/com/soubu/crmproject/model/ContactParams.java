package com.soubu.crmproject.model;

import android.text.TextUtils;

import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactParams extends ObjectToMapInterface implements Serializable, Cloneable{

    String name;
    CustomerParams customer;
    String customerId;
    String position;
    String phone;
    String mobile;
    String qq;
    String wechat;
    String wangwang;
    String email;
    String note;
    String department;
    Date createdAt;
    Date updatedAt;
    Date touchedAt;
    String touchedCount;
    String id;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public CustomerParams getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerParams customer) {
        this.customer = customer;
    }

    public Date getTouchedAt() {
        return touchedAt;
    }

    public void setTouchedAt(Date touchedAt) {
        this.touchedAt = touchedAt;
    }

    public String getTouchedCount() {
        return touchedCount;
    }

    public void setTouchedCount(String touchedCount) {
        this.touchedCount = touchedCount;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContactParams clone() throws CloneNotSupportedException{
        return (ContactParams)super.clone();
    }

    public Contact copyToContact(){
        Contact contact = new Contact();
        contact.setContact_id(id);
        contact.setMobile(mobile);
        contact.setCreatedAt(createdAt);
        contact.setCustomer(customer.id);
        contact.setDepartment(department);
        contact.setName(name);
        contact.setPhone(phone);
        contact.setPosition(position);
        contact.setQq(qq);
        contact.setUpdatedAt(updatedAt);
        contact.setWangwang(wangwang);
        contact.setWechat(wechat);
        contact.setTouchedAt(touchedAt);
        contact.setTouchedCount(touchedCount);
        contact.setCustomer_name(customer.name);
        return contact;
    }

    public boolean equalsContact(Contact contact) {
        //因为联系时间最频繁,所以最先检查
        if(contact.getTouchedAt() != null && contact.getTouchedAt().compareTo(touchedAt) != 0){
            return false;
        }
        if(contact.getUpdatedAt() != null && contact.getUpdatedAt().compareTo(updatedAt) != 0){
            return false;
        }
        if(!TextUtils.equals(contact.getContact_id(), id)){
            return false;
        }
        if(!TextUtils.equals(contact.getMobile(), mobile)){
            return false;
        }
        if(!TextUtils.equals(contact.getCustomer(), customer.id)){
            return false;
        }
        if(!TextUtils.equals(contact.getCustomer_name(), customer.name)){
            return false;
        }
        if(!TextUtils.equals(contact.getDepartment(), department)){
            return false;
        }
        if(!TextUtils.equals(contact.getName(), name)){
            return false;
        }
        if(!TextUtils.equals(contact.getPhone(), phone)){
            return false;
        }
        if(!TextUtils.equals(contact.getPosition(), position)){
            return false;
        }
        if(!TextUtils.equals(contact.getQq(), qq)){
            return false;
        }
        if(!TextUtils.equals(contact.getWangwang(), wangwang)){
            return false;
        }
        if(!TextUtils.equals(contact.getWechat(), wechat)){
            return false;
        }
        if(contact.getCreatedAt() != null && contact.getCreatedAt().compareTo(createdAt) != 0){
            return false;
        }
        return true;
    }
}
