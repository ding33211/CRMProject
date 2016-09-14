package com.soubu.crmproject.server;

import com.soubu.crmproject.model.BackSalesParams;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.model.GetPageResp;
import com.soubu.crmproject.model.UserParams;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("opportunities")
    Call<GetPageResp<ClueParams[]>> getClue(@Query("source") String source,//线索来源
                                            @Query("status") String status,//线索状态
                                            @Query("page") Integer page,//页数
                                            @Query("sort") String sort,//排序项
                                            @Query("order") String order,//顺序
                                            @Query("related") String related,//数据相关
                                            @Query("count") Integer count, //每页条数,默认10
                                            @Query("q") String search);//搜索

    //添加线索
    @Headers({"Content-type:application/json"})
    @POST("opportunities")
    Call<GetPageResp<ClueParams[]>> addClue(@Body ClueParams params, @Header("sign") String sign);

    //修改线索
    @FormUrlEncoded
    @PUT("opportunities/{id}")
    Call<GetPageResp<ClueParams[]>> updateClue(@Path("id") String id, @FieldMap Map<String, String> names, @Header("sign") String sign);


    //获取客户
    @GET("customers")
    Call<GetPageResp<CustomerParams[]>> getCustomer(@Query("type") String type,//客户类型
                                                    @Query("source") String source,//线索来源
                                                    @Query("size") String size,//规模
                                                    @Query("industry") String industry,//行业
                                                    @Query("status") String status,//客户状态
                                                    @Query("page") Integer page,//页数
                                                    @Query("sort") String sort,//排序项
                                                    @Query("order") String order,//顺序
                                                    @Query("related") String related,//数据相关
                                                    @Query("count") Integer count,//每页条数,默认10
                                                    @Query("q") String search);//搜索

    //获取客户
    @GET("customers/{id}")
    Call<GetPageResp<CustomerParams[]>> getCustomerSpec(@Path("id") String id);//搜索

    //添加客户
    @Headers({"Content-type:application/json"})
    @POST("customers")
    Call<GetPageResp<CustomerParams[]>> addCustomer(@Body CustomerParams params, @Header("sign") String sign);

    //修改客户
    @FormUrlEncoded
    @PUT("customers/{id}")
    Call<GetPageResp<CustomerParams[]>> updateCustomer(@Path("id") String id, @FieldMap Map<String, String> names, @Header("sign") String sign);


    //获取商机
    @GET("deals")
    Call<GetPageResp<BusinessOpportunityParams[]>> getBusinessOpportunity(@Query("type") String type,//商机类型
                                                                          @Query("source") String source,//商机来源
                                                                          @Query("status") String status,//商机状态
                                                                          @Query("page") Integer page,//页数
                                                                          @Query("sort") String sort,//排序项
                                                                          @Query("order") String order,//顺序
                                                                          @Query("related") String related,//数据相关
                                                                          @Query("count") Integer count,//每页条数,默认10
                                                                          @Query("customer") String customer,//客户id
                                                                          @Query("q") String search);//搜索


    //添加商机
    @Headers({"Content-type:application/json"})
    @POST("deals")
    Call<GetPageResp<BusinessOpportunityParams[]>> addBusinessOpportunity(@Body BusinessOpportunityParams params, @Header("sign") String sign);

    //修改商机
    @FormUrlEncoded
    @PUT("deals/{id}")
    Call<GetPageResp<BusinessOpportunityParams[]>> updateBusinessOpportunity(@Path("id") String id, @FieldMap Map<String, String> names,
                                                                             @Header("sign") String sign);


    //获取合同
    @GET("contracts")
    Call<GetPageResp<ContractParams[]>> getContract(@Query("type") String type,//合同类型
                                                    @Query("payMethod") String payMethod,//付款方式
                                                    @Query("status") String status,//合同状态
                                                    @Query("receivedPayType") String receivedPayType,//汇款方式
                                                    @Query("page") Integer page,//页数
                                                    @Query("sort") String sort,//排序项
                                                    @Query("order") String order,//顺序
                                                    @Query("related") String related,//数据相关
                                                    @Query("count") Integer count,
                                                    @Query("q") String seach);//每页条数,默认10

    //添加合同
    @Headers({"Content-type:application/json"})
    @POST("contracts")
    Call<GetPageResp<ContractParams[]>> addContract(@Body ContractParams params, @Header("sign") String sign);

    //修改合同
    @FormUrlEncoded
    @PUT("contracts/{id}")
    Call<GetPageResp<ContractParams[]>> updateContract(@Path("id") String id, @FieldMap Map<String, String> names, @Header("sign") String sign);


    //添加跟进
    @Headers({"Content-type:application/json"})
    @POST("records")
    Call<GetPageResp<FollowParams[]>> addFollow(@Body FollowParams params, @Header("sign") String sign);


    //获取线索相关的跟进
    @GET("opportunities/{id}/records")
    Call<GetPageResp<FollowParams[]>> getClueFollow(@Path("id") String id,
                                                    @Query("status") String status,//线索状态
                                                    @Query("sort") String sort,//排序项
                                                    @Query("order") String order,//顺序
                                                    @Query("page") Integer page,//页数
                                                    @Query("count") Integer count);//每页条数,默认10

    //获取线索相关的跟进
    @GET("customers/{id}/records")
    Call<GetPageResp<FollowParams[]>> getCustomerFollow(@Path("id") String id,
                                                        @Query("status") String status,//线索状态
                                                        @Query("sort") String sort,//排序项
                                                        @Query("order") String order,//顺序
                                                        @Query("page") Integer page,//页数
                                                        @Query("count") Integer count);//每页条数,默认10

    //获取线索相关的跟进
    @GET("deals/{id}/records")
    Call<GetPageResp<FollowParams[]>> getBusinessOpportunityFollow(@Path("id") String id,
                                                                   @Query("status") String status,//线索状态
                                                                   @Query("sort") String sort,//排序项
                                                                   @Query("order") String order,//顺序
                                                                   @Query("page") Integer page,//页数
                                                                   @Query("count") Integer count);//每页条数,默认10

    //获取线索相关的跟进
    @GET("contracts/{id}/records")
    Call<GetPageResp<FollowParams[]>> getContractFollow(@Path("id") String id,
                                                        @Query("status") String status,//线索状态
                                                        @Query("sort") String sort,//排序项
                                                        @Query("order") String order,//顺序
                                                        @Query("page") Integer page,//页数
                                                        @Query("count") Integer count);//每页条数,默认10

    //添加已回款
    @Headers({"Content-type:application/json"})
    @POST("contracts/{id}/ccs")
    Call<GetPageResp<BackSalesParams[]>> addBackSales(@Body BackSalesParams params, @Header("sign") String sign, @Path("id") String id);


    //获取客户下联系人
    @GET("contacts")
    Call<GetPageResp<ContactParams[]>> getContact(@Query("customer") String customerId,//客户id
                                                  @Query("page") Integer page,//页数
                                                  @Query("sort") String sort,//排序项
                                                  @Query("order") String order,//顺序
                                                  @Query("related") String related,//数据相关
                                                  @Query("count") Integer count);//每页条数,默认10

    //添加联系人
    @Headers({"Content-type:application/json"})
    @POST("contacts")
    Call<GetPageResp<ContactParams[]>> addContact(@Body ContactParams params, @Header("sign") String sign);

    //修改线索
    @FormUrlEncoded
    @PUT("contacts/{id}")
    Call<GetPageResp<ContactParams[]>> updateContact(@Path("id") String id, @FieldMap Map<String, String> names, @Header("sign") String sign);


    //注册
    @Headers({"Content-type:application/json"})
    @POST("users/signup")
    Call<GetPageResp<UserParams[]>> register(@Body UserParams params);

    //登录
    @Headers({"Content-type:application/json"})
    @POST("users/signin")
    Call<GetPageResp<UserParams[]>> login(@Body UserParams params);


    //获取线索公海
    @GET("opportunities/public")
    Call<GetPageResp<ClueParams[]>> getClueHighSeas(@Query("source") String source,//线索来源
                                            @Query("status") String status,//线索状态
                                            @Query("page") Integer page,//页数
                                            @Query("sort") String sort,//排序项
                                            @Query("order") String order,//顺序
                                            @Query("count") Integer count, //每页条数,默认10
                                            @Query("q") String search);//搜索


    //抢线索公海
    @PUT("opportunities/{id}/grab")
    Call<GetPageResp<ClueParams[]>> rushClueHighSeas(@Path("id") String id, @Header("sign") String sign);
}
