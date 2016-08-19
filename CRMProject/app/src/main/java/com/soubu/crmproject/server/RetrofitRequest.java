package com.soubu.crmproject.server;

import com.soubu.crmproject.base.greendao.Clue;
import com.soubu.crmproject.model.ClueTest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Retrofit的网络请求类
 * Created by dingsigang on 16-8-17.
 */
public class RetrofitRequest {

    /**
     * 获取线索列表
     * @param page
     */
    public static void getClueList(int page) {
        ArrayList<ClueTest> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            ClueTest clue = new ClueTest();
            clue.setTitle("title1");
            clue.setCustomer("老吴");
            clue.setDesc("泡脚擦莫i从jamcamvio简单v米哦拍卖场  买点阿妈佛门奥if阿公欧赔哦马嵬坡方面马戏");
            clue.setPrincipal("x小吴");
            clue.setFollow_result(0);
            clue.setFollow_state(0);
            list.add(clue);
        }

        EventBus.getDefault().post(list);
//        Call<T> call = RetrofitService.getInstance()
//                .createApi()
//                .getNewsList(RetrofitService.getCacheControl(), page, CHANNEL_ID, CHANNEL_NAME);
//
//        call.enqueue(new Callback<T>() {
//            @Override
//            public void onResponse(Call<T> call, Response<T> response) {
//                EventBus.getDefault().post(response.body().);
//            }
//
//            @Override
//            public void onFailure(Call<T> call, Throwable t) {
//                EventBus.getDefault().post(t);
//            }
//
//        });
    }

}
