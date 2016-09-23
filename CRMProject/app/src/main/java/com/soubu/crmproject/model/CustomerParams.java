package com.soubu.crmproject.model;

import com.soubu.crmproject.server.ObjectToMapInterface;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingsigang on 16-8-26.
 */
public class CustomerParams extends ObjectToMapInterface implements Serializable, Cloneable{
    String name;
    String property;
    String type;
    String source;
    String phone;
    String fax;
    String cemail;
    String cname;
    String cmobile;
    String cphone;
    String cqq;
    String cwechat;
    String address;
    String postcode;
    String website;
    String industry;
    String model;
    String manager;
    String size;
    String revenue;
    String dealsCount;
    String contractsCount;
    String tOpportunityId;
    UserParams user;
    UserParams creator;
    String userId;

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCmobile() {
        return cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCqq() {
        return cqq;
    }

    public void setCqq(String cqq) {
        this.cqq = cqq;
    }

    public String getCwechat() {
        return cwechat;
    }

    public void setCwechat(String cwechat) {
        this.cwechat = cwechat;
    }

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

    public String gettOpportunity() {
        return tOpportunityId;
    }

    public void settOpportunity(String opportunity) {
        this.tOpportunityId = opportunity;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDealsCount() {
        return dealsCount;
    }

    public void setDealsCount(String dealsCount) {
        this.dealsCount = dealsCount;
    }

    public String getContractsCount() {
        return contractsCount;
    }

    public void setContractsCount(String contractsCount) {
        this.contractsCount = contractsCount;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    String[] products;
    String intro;
    String status;
    Date createdAt;
    Date updatedAt;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }


    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public CustomerParams clone() throws CloneNotSupportedException{
        return (CustomerParams)super.clone();
    }
}
