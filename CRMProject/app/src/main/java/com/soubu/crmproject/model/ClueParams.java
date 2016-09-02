package com.soubu.crmproject.model;

import android.util.Log;

import com.google.gson.Gson;
import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-17.
 */
public class ClueParams extends ObjectToMapInterface implements Serializable, Cloneable{
//
//    public static final String SOURCE_ADVERTISEMENT = "ADVERTISEMENT";
//    public static final String SOURCE_SOCIAL_MEDIA = "SOCIAL_MEDIA";
//    public static final String SOURCE_WORKSHOP = "WORKSHOP";
//    public static final String SOURCE_SEARCH_ENGINE = "SEARCH_ENGINE";
//    public static final String SOURCE_CUSTOMER_REFERRAL = "CUSTOMER_REFERRAL";
//    public static final String SOURCE_BUSINESS_DEVELOPMENT = "BUSINESS_DEVELOPMENT";
//    public static final String SOURCE_AGENT = "AGENT";
//    public static final String SOURCE_OTHER = "OTHER";
//
//    public static final String STATUS_UNHANDLED = "UNHANDLED";
//    public static final String STATUS_CONTACT_VALID = "CONTACT_VALID";
//    public static final String STATUS_CONTACT_INVALID = "CONTACT_INVALID";
//    public static final String STATUS_CLOSED = "CLOSED";

    private String companyName;
    private String manager;
    private String contactName;
    private String department;
    private String position;
    private String phone;
    private String mobile;
    private String qq;
    private String wechat;
    private String wangwang;
    private String email;
    private String website;
    private String address;
    private String postcode;
    private String note;
    private String source;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String id;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ClueParams clone() throws CloneNotSupportedException{
        return (ClueParams)super.clone();
    }

}
