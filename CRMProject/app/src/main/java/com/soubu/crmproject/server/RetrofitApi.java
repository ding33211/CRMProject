package com.soubu.crmproject.server;

import com.soubu.crmproject.model.GetCluePageResp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 使用retrofit的Api接口
 */
public interface RetrofitApi {

    //example
//    @FormUrlEncoded
//    @POST("Order/post_order")
//    Call<RetrofitApiResp<xxxx>> postOrder(@Field("params") String params);

//    @GET(BizInterface.NEWS_URL)
//    @Headers("apikey: " + BizInterface.API_KEY)
//    Call<RetrofitRequest.ShowApiResponse<RetrofitRequest.ShowApiNews>> getNewsList(@Header("Cache-Control") String cacheControl,
//                                                                                           @Query("page") int page,
//                                                                                           @Query("channelId") String channelId,//新闻频道id，必须精确匹配
//                                                                                           @Query("channelName") String channelName);//新闻频道名称，可模糊匹配


    //获取线索
//    @FormUrlEncoded
    @GET("opportunities")
    Call<GetCluePageResp> getClue(@Query("source") String source,//线索来源
                                  @Query("status") String status);//线索状态

    @GET("opportunities")
    Call<GetCluePageResp> getClue(@QueryMap Map<String, String> param);//线索状态


    //获取线索
    @FormUrlEncoded
    @POST("opportunities")
    Call<GetCluePageResp> postClue(@Field("params") String params);


}
