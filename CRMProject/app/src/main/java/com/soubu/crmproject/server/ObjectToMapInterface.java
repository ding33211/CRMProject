package com.soubu.crmproject.server;

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
            for(Map.Entry entry : object.entrySet()){
                map.put(entry.getKey().toString(), String.valueOf(entry.getValue()).substring(1, entry.getValue().toString().length() - 1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
