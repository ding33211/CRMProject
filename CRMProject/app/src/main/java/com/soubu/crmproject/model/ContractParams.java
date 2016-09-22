package com.soubu.crmproject.model;

import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingsigang on 16-8-29.
 */
public class ContractParams extends ObjectToMapInterface implements Serializable, Cloneable{

    String title;
    String manager;
    CustomerParams customer;
    BusinessOpportunityParams deal;
    String product;
    String amountPrice;
    Date startedAt;
    Date finishedAt;
    String serialNumber;
    String payMethod;
    String status;
    String reviewStatus;
    String clientSignedPerson;
    String signedPerson;
    Date closedAt;
    Object[] attachments;
    String note;
    Date createdAt;
    Date updatedAt;
    String type;
    String id;
    String tDeal;
    String customerId;
    String dealId;
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

    public BusinessOpportunityParams getDeal() {
        return deal;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public void setDeal(BusinessOpportunityParams deal) {
        this.deal = deal;
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

    public String gettDeal() {
        return tDeal;
    }

    public void settDeal(String tDeal) {
        this.tDeal = tDeal;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Object[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Object[] attachments) {
        this.attachments = attachments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientSignedPerson() {
        return clientSignedPerson;
    }

    public void setClientSignedPerson(String clientSignedPerson) {
        this.clientSignedPerson = clientSignedPerson;
    }

    public String getSignedPerson() {
        return signedPerson;
    }

    public void setSignedPerson(String signedPerson) {
        this.signedPerson = signedPerson;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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

    public ContractParams clone() throws CloneNotSupportedException{
        return (ContractParams)super.clone();
    }
}
