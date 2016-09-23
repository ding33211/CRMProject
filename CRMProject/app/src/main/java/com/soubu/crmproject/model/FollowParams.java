package com.soubu.crmproject.model;

import com.soubu.crmproject.server.ObjectToMapInterface;

import java.util.Date;

/**
 * Created by dingsigang on 16-8-31.
 */
public class FollowParams extends ObjectToMapInterface {
    UserParams user;
    String username;
    Object entity;
    String entityId;
    String entityType;
    String entityName;
    String status;
    String content;
    String method;
    Date followupAt;
    Date createdAt;
    Date updatedAt;
    String id;
    String title;
    String type;
    ContactParams contact;
    String contactId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public ContactParams getContact() {
        return contact;
    }

    public void setContact(ContactParams contact) {
        this.contact = contact;
    }

    public UserParams getUser() {
        return user;
    }

    public void setUser(UserParams user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getFollowupAt() {
        return followupAt;
    }

    public void setFollowupAt(Date followupAt) {
        this.followupAt = followupAt;
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
}
