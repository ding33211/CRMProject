package com.soubu.CRMProject.base.greendao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "Clue".
 */
@Entity(nameInDb = "Clue")
public class Clue {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String clue_id;
    private String wechat;
    private String phone;
    private String landline;
    private String post;
    private String department;
    private String company;
    private Integer from;
    private String name;
    private String principal;
    private String area;
    private String address;
    private String zip_code;
    private String remark;
    private String network;
    private String email;
    private String wangwang;
    private Integer follow_state;
    private String qq;

    @Generated
    public Clue() {
    }

    public Clue(Long id) {
        this.id = id;
    }

    @Generated
    public Clue(Long id, String clue_id, String wechat, String phone, String landline, String post, String department, String company, Integer from, String name, String principal, String area, String address, String zip_code, String remark, String network, String email, String wangwang, Integer follow_state, String qq) {
        this.id = id;
        this.clue_id = clue_id;
        this.wechat = wechat;
        this.phone = phone;
        this.landline = landline;
        this.post = post;
        this.department = department;
        this.company = company;
        this.from = from;
        this.name = name;
        this.principal = principal;
        this.area = area;
        this.address = address;
        this.zip_code = zip_code;
        this.remark = remark;
        this.network = network;
        this.email = email;
        this.wangwang = wangwang;
        this.follow_state = follow_state;
        this.qq = qq;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getClue_id() {
        return clue_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setClue_id(@NotNull String clue_id) {
        this.clue_id = clue_id;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    public Integer getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(Integer follow_state) {
        this.follow_state = follow_state;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

}
