
package com.soubu.crmproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseData<T> {

    @SerializedName("data")
    @Expose
    public T data;


}
