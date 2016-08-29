package com.soubu.crmproject.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingsigang on 16-8-29.
 */
public class ContractParams implements Serializable, Cloneable {

    String title;
    String manager;
    String customer;
    String deal;
    String product;
    String amountPrice;
    Date startedAt;
    Date finishedAt;
    String serialNumber;
    String payMethod;
    String status;
    String clientSignedPerson;
    String signedPerson;
    Date closedAt;
    Object[] attachments;
    String note;

    public Object[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Object[] attachments) {
        this.attachments = attachments;
    }

    Date createdAt;
    Date updatedAt;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String id;

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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
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
