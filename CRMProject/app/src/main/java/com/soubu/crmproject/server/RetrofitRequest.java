package com.soubu.crmproject.server;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soubu.crmproject.common.ApiConfig;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.GetCluePageResp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit的网络请求类
 * Created by dingsigang on 16-8-17.
 */
public class RetrofitRequest {

    /**
     * 获取线索列表
     * @param page
     */
    public static void getClueList(int page, String source, String status) {
//        ArrayList<ClueParams> list = new ArrayList<>();
//        for(int i = 0; i < 10; i++){
//            ClueParams clue = new ClueParams();
//            list.add(clue);
//        }
//
//        EventBus.getDefault().post(list);
        Map<String, String> map = new HashMap<>();
        if(source != null){
            map.put("source", source);
        }
        if(status != null){
            map.put("status", status);
        }

        Call<GetCluePageResp> call = RetrofitService.getInstance()
                .createApi()
//                .getClue(map);
        .getClue(null, null);
//        .getClue(new Gson().toJson(new ClueParams()));

        call.enqueue(new Callback<GetCluePageResp>() {
            @Override
            public void onResponse(Call<GetCluePageResp> call, Response<GetCluePageResp> response) {
                Log.e("xxxxxxxxxxxxx", "1111111111111");
                if(response.body() == null){
                    Log.e("xxxxxxxxxxxx", "empty!!!!!!!!!");
                    return;
                }
                int status = response.body().getStatus();
                if (status == ApiConfig.RELUST_OK) {
                    EventBus.getDefault().post(response.body().data);
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetCluePageResp> call, Throwable t) {
                Log.e("xxxxxxxxxxxxx", "1111111111111" + t.toString());
                EventBus.getDefault().post(t);
            }



        });
    }

}
