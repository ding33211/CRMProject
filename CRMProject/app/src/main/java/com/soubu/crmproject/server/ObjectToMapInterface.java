package com.soubu.crmproject.server;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingsigang on 16-9-2.
 */
public abstract class ObjectToMapInterface {

    public Map<String, String> getMap(){
        Map<String, String> map = new HashMap<>();
        try{
            JsonElement element = new Gson().toJsonTree(this);
            JsonObject object = element.getAsJsonObject();
            for(Map.Entry<String, JsonElement> entry : object.entrySet()){
                map.put(entry.getKey().toString(), entry.getValue().getAsString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
