
package com.soubu.crmproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("data")
    @Expose
    public List<ClueParams> data;


}
