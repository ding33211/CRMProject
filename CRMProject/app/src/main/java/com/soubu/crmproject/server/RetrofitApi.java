package com.soubu.crmproject.server;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 *  使用retrofit的Api接口
 *
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


}
