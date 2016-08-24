package com.soubu.crmproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class GetCluePageResp extends BaseResp {
//    @SerializedName("count")
//    @Expose
//    public int count;

    @SerializedName("result")
    @Expose
    private Result result;

    /**
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Result result) {
        this.result = result;
    }


}
