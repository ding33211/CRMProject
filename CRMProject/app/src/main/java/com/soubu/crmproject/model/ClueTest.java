package com.soubu.crmproject.model;

/**
 * Created by dingsigang on 16-8-17.
 */
public class ClueTest {
    private String title;
    private long time;
    private String desc;
    private String customer;
    private String principal;
    private int follow_state;
    private int follow_result;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public int getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(int follow_state) {
        this.follow_state = follow_state;
    }

    public int getFollow_result() {
        return follow_result;
    }

    public void setFollow_result(int follow_result) {
        this.follow_result = follow_result;
    }
}
