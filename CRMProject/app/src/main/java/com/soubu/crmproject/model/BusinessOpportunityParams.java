package com.soubu.crmproject.model;

import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingsigang on 16-8-29.
 */
public class BusinessOpportunityParams extends ObjectToMapInterface implements Serializable, Cloneable {
    String title;
    CustomerParams customer;
    String product;
    String amountPrice;
    Date closingAt;
    String manager;
    String source;
    String type;
    String status;
    String note;
    String gotAt;
    Date createdAt;
    Date updatedAt;
    String id;
    String customerId;
    UserParams user;
    UserParams creator;
    String userId;

    public UserParams getUser() {
        return user;
    }

    public void setUser(UserParams user) {
        this.user = user;
    }

    public UserParams getCreator() {
        return creator;
    }

    public void setCreator(UserParams creator) {
        this.creator = creator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(String amountPrice) {
        this.amountPrice = amountPrice;
    }

    public Date getClosingAt() {
        return closingAt;
    }

    public void setClosingAt(Date closingAt) {
        this.closingAt = closingAt;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGotAt() {
        return gotAt;
    }

    public void setGotAt(String gotAt) {
        this.gotAt = gotAt;
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

    public BusinessOpportunityParams clone() throws CloneNotSupportedException{
        return (BusinessOpportunityParams)super.clone();
    }
}
