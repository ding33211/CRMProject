package com.soubu.crmproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dingsigang on 16-8-24.
 */
public class BaseResp {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("msg")
    @Expose
    public String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
