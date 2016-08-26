package com.soubu.crmproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class GetPageResp<T> extends BaseResp {
//    @SerializedName("count")
//    @Expose
//    public int count;

    @SerializedName("result")
    @Expose
    private BaseData<T> result;

    /**
     * @return The result
     */
    public BaseData<T> getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(BaseData<T> result) {
        this.result = result;
    }


}
