package com.soubu.crmproject.server;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.soubu.crmproject.model.ContactParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingsigang on 16-9-2.
 */
public abstract class ObjectToMapInterface {

    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        try {
            JsonElement element = new Gson().toJsonTree(this);
            JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                if(TextUtils.isEmpty(entry.getValue().getAsString())){
                    continue;
                }
                map.put(entry.getKey().toString(), entry.getValue().getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //customer,deal,user获得object类型,上传需要字符传类型
//        if (map.containsKey("customerId")) {
//            map.put("customer", map.get("customerId"));
//            map.remove("customerId");
//        }
//        if (map.containsKey("dealId")) {
//            map.put("deal", map.get("dealId"));
//            map.remove("dealId");
//        }
//        if (map.containsKey("userId")) {
//            map.put("user", map.get("userId"));
//            map.remove("userId");
//        }
        return map;
    }
}
