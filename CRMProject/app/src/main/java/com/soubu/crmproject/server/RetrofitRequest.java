package com.soubu.crmproject.server;

import android.util.Log;

import com.soubu.crmproject.common.ApiConfig;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.BaseData;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.GetPageResp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit的网络请求类
 * Created by dingsigang on 16-8-17.
 */
public class RetrofitRequest {
    public static final String TAG = "RetrofitRequest";


    private static RetrofitRequest mInstance;

    public static RetrofitRequest getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitRequest.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitRequest();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取线索列表
     *
     * @param page
     */
    public void getClueList(Integer page, String source, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<List<ClueParams>>> call = RetrofitService.getInstance()
                .createApi()
                .getClue(source, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增线索记录
     * @param clue 线索对象
     */
    public void addClue(ClueParams clue) {
        Call<GetPageResp<List<ClueParams>>> call = RetrofitService.getInstance()
                .createApi()
                .addClue(clue);
        enqueueClue(call, false);
    }

    /**
     * 更新线索
     * @param id  线索id
     * @param map  更新项
     */
    public  void updateClue(String id, Map<String, String> map) {
        Call<GetPageResp<List<ClueParams>>> call = RetrofitService.getInstance()
                .createApi()
                .updateClue(id, map);
        enqueueClue(call, true);
    }


    private <T> void enqueueClue(Call<GetPageResp<T>> call, final boolean needEventPost) {
        call.enqueue(new Callback<GetPageResp<T>>() {
            @Override
            public void onResponse(Call<GetPageResp<T>> call, Response<GetPageResp<T>> response) {
                Log.e(TAG, "1111111111111");
                if (response.body() == null) {
                    Log.e(TAG, "empty!!!!!!!!!");
                    return;
                }
                int status = response.body().getStatus();
                if (status == ApiConfig.RELUST_OK) {
                    if(needEventPost){
                        EventBus.getDefault().post(response.body().getResult().data);
                    }
                    return;
                } else {
                    Log.e(TAG, response.body().errors.toString());
                }
            }
            @Override
            public void onFailure(Call<GetPageResp<T>> call, Throwable t) {
                Log.e(TAG, "1111111111111" + t.toString());
                EventBus.getDefault().post(t);
            }

        });
    }


    /**
     * 获取客户列表
     *
     */
    public void getCustomerList(Integer page, String type, String source, String size, String industry, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<List<CustomerParams>>> call = RetrofitService.getInstance()
                .createApi()
                .getCustomer(type, source, size, industry, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增客户记录
     * @param customerParams 客户对象
     */
    public void addCustomer(CustomerParams customerParams) {
        Call<GetPageResp<List<CustomerParams>>> call = RetrofitService.getInstance()
                .createApi()
                .addCustomer(customerParams);
        enqueueClue(call, false);
    }

    /**
     * 更新客户
     * @param id  客户id
     * @param map  更新项
     */
    public  void updateCustomer(String id, Map<String, String> map) {
        Call<GetPageResp<List<CustomerParams>>> call = RetrofitService.getInstance()
                .createApi()
                .updateCustomer(id, map);
        enqueueClue(call, true);
    }

    /**
     * 获取商机列表
     *
     */
    public void getBusinessOpportunityList(Integer page, String type, String source, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<List<BusinessOpportunityParams>>> call = RetrofitService.getInstance()
                .createApi()
                .getBusinessOpportunity(type, source, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }


    /**
     * 新增商机记录
     * @param businessOpportunityParams 商机对象
     */
    public void addBusinessOpportunity(BusinessOpportunityParams businessOpportunityParams) {
        Call<GetPageResp<List<BusinessOpportunityParams>>> call = RetrofitService.getInstance()
                .createApi()
                .addBusinessOpportunity(businessOpportunityParams);
        enqueueClue(call, false);
    }

    /**
     * 更新商机
     * @param id  商机id
     * @param map  更新项
     */
    public  void updateBusinessOpportunity(String id, Map<String, String> map) {
        Call<GetPageResp<List<BusinessOpportunityParams>>> call = RetrofitService.getInstance()
                .createApi()
                .updateBusinessOpportunity(id, map);
        enqueueClue(call, true);
    }

    /**
     * 获取合同列表
     *
     */
    public void getContractList(Integer page, String type, String payMethod, String status, String receivedPayMethod, String sort, String order, String related, Integer count) {
        Call<GetPageResp<List<ContractParams>>> call = RetrofitService.getInstance()
                .createApi()
                .getContract(type, payMethod, status, receivedPayMethod, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增合同记录
     * @param contractParams 合同对象
     */
    public void addContract(ContractParams contractParams) {
        Call<GetPageResp<List<ContractParams>>> call = RetrofitService.getInstance()
                .createApi()
                .addContract(contractParams);
        enqueueClue(call, false);
    }

    /**
     * 更新合同
     * @param id  合同id
     * @param map  更新项
     */
    public  void updateContract(String id, Map<String, String> map) {
        Call<GetPageResp<List<ContractParams>>> call = RetrofitService.getInstance()
                .createApi()
                .updateContract(id, map);
        enqueueClue(call, true);
    }

}
