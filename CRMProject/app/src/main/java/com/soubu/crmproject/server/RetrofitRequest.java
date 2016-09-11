package com.soubu.crmproject.server;

import android.util.Log;

import com.soubu.crmproject.common.ApiConfig;
import com.soubu.crmproject.model.BackSalesParams;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.model.GetPageResp;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.utils.ConvertUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
     * 注册
     *
     * @param params 用户对象
     */
    public void register(UserParams params) {
        Call<GetPageResp<UserParams[]>> call = RetrofitService.getInstance()
                .createApi(false)
                .register(params);
        enqueueClue(call, true);
    }

    /**
     * 注册
     *
     * @param params 用户对象
     */
    public void login(UserParams params) {
        Call<GetPageResp<UserParams[]>> call = RetrofitService.getInstance()
                .createApi(false)
                .login(params);
        enqueueClue(call, true);
    }


    /**
     * 获取线索列表
     *
     * @param page
     */
    public void getClueList(Integer page, String source, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<ClueParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getClue(source, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增线索记录
     *
     * @param clue 线索对象
     */
    public void addClue(ClueParams clue) {
        Call<GetPageResp<ClueParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addClue(clue, ConvertUtil.hmacsha256(clue.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 更新线索
     *
     * @param id  线索id
     * @param map 更新项
     */
    public void updateClue(String id, Map<String, String> map) {
        Call<GetPageResp<ClueParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .updateClue(id, map, ConvertUtil.hmacsha256(map, ApiConfig.getToken()));
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
                    if (needEventPost) {
                        EventBus.getDefault().post(response.body().getResult().data);
                    }
                    return;
                } else {
                    if (response.body().errors != null) {
                        Log.e(TAG, response.body().errors.toString());
                    } else if (response.body().getRawString() != null && response.body().getSign() != null) {
                        Log.e(TAG, response.body().getRawString() + "       " + response.body().getSign());
                    }
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
     */
    public void getCustomerList(Integer page, String type, String source, String size, String industry, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<CustomerParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getCustomer(type, source, size, industry, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增客户记录
     *
     * @param customerParams 客户对象
     */
    public void addCustomer(CustomerParams customerParams) {
        Call<GetPageResp<CustomerParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addCustomer(customerParams, ConvertUtil.hmacsha256(customerParams.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 更新客户
     *
     * @param id  客户id
     * @param map 更新项
     */
    public void updateCustomer(String id, Map<String, String> map) {
        Call<GetPageResp<CustomerParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .updateCustomer(id, map, ConvertUtil.hmacsha256(map, ApiConfig.getToken()));
        enqueueClue(call, true);
    }

    /**
     * 获取商机列表
     */
    public void getBusinessOpportunityList(Integer page, String type, String source, String status, String sort, String order, String related, Integer count) {
        Call<GetPageResp<BusinessOpportunityParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getBusinessOpportunity(type, source, status, page, sort, order, related, count);
        enqueueClue(call, true);
    }


    /**
     * 新增商机记录
     *
     * @param businessOpportunityParams 商机对象
     */
    public void addBusinessOpportunity(BusinessOpportunityParams businessOpportunityParams) {
        Call<GetPageResp<BusinessOpportunityParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addBusinessOpportunity(businessOpportunityParams, ConvertUtil.hmacsha256(businessOpportunityParams.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 更新商机
     *
     * @param id  商机id
     * @param map 更新项
     */
    public void updateBusinessOpportunity(String id, Map<String, String> map) {
        Call<GetPageResp<BusinessOpportunityParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .updateBusinessOpportunity(id, map, ConvertUtil.hmacsha256(map, ApiConfig.getToken()));
        enqueueClue(call, true);
    }

    /**
     * 获取合同列表
     */
    public void getContractList(Integer page, String type, String payMethod, String status, String receivedPayMethod, String sort, String order, String related, Integer count) {
        Call<GetPageResp<ContractParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getContract(type, payMethod, status, receivedPayMethod, page, sort, order, related, count);
        enqueueClue(call, true);
    }

    /**
     * 新增合同记录
     *
     * @param contractParams 合同对象
     */
    public void addContract(ContractParams contractParams) {
        Call<GetPageResp<ContractParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addContract(contractParams, ConvertUtil.hmacsha256(contractParams.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 更新合同
     *
     * @param id  合同id
     * @param map 更新项
     */
    public void updateContract(String id, Map<String, String> map) {
        Call<GetPageResp<ContractParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .updateContract(id, map, ConvertUtil.hmacsha256(map, ApiConfig.getToken()));
        enqueueClue(call, true);
    }


    /**
     * 新增跟进
     *
     * @param followParams 跟进对象
     */
    public void addFollow(FollowParams followParams) {
        Call<GetPageResp<FollowParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addFollow(followParams, ConvertUtil.hmacsha256(followParams.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 获取线索的跟进
     *
     * @param id 线索id
     */
    public void getClueFollow(String id, String status, String sort, String order, Integer page, Integer count) {
        Call<GetPageResp<FollowParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getClueFollow(id, status, sort, order, page, count);
        enqueueClue(call, true);
    }

    /**
     * 获取客户的跟进
     *
     * @param id 线索id
     */
    public void getCustomerFollow(String id, String status, String sort, String order, Integer page, Integer count) {
        Call<GetPageResp<FollowParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getCustomerFollow(id, status, sort, order, page, count);
        enqueueClue(call, true);
    }

    /**
     * 获取商机的跟进
     *
     * @param id 线索id
     */
    public void getBusinessOpportunityFollow(String id, String status, String sort, String order, Integer page, Integer count) {
        Call<GetPageResp<FollowParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getBusinessOpportunityFollow(id, status, sort, order, page, count);
        enqueueClue(call, true);
    }

    /**
     * 获取合同的跟进
     *
     * @param id 线索id
     */
    public void getContractFollow(String id, String status, String sort, String order, Integer page, Integer count) {
        Call<GetPageResp<FollowParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getContractFollow(id, status, sort, order, page, count);
        enqueueClue(call, true);
    }


    /**
     * 新增已回款记录
     *
     * @param params 回款对象
     * @param id     合同id
     */
    public void addBackSales(BackSalesParams params, String id) {
        Call<GetPageResp<BackSalesParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addBackSales(params, ConvertUtil.hmacsha256(params.getMap(), ApiConfig.getToken()), id);
        enqueueClue(call, false);
    }


    /**
     * 获取客户下联系人列表
     *
     * @param page
     */
    public void getContactList(Integer page, String customerId) {
        Call<GetPageResp<ContactParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .getContact(customerId, page, null, null, null, null);
        enqueueClue(call, true);
    }

    /**
     * 新增联系人
     *
     * @param params 联系人对象
     */
    public void addContact(ContactParams params) {
        Call<GetPageResp<ContactParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .addContact(params, ConvertUtil.hmacsha256(params.getMap(), ApiConfig.getToken()));
        enqueueClue(call, false);
    }

    /**
     * 更新联系人
     *
     * @param id  线索id
     * @param map 更新项
     */
    public void updateContact(String id, Map<String, String> map) {
        Call<GetPageResp<ContactParams[]>> call = RetrofitService.getInstance()
                .createApi(true)
                .updateContact(id, map, ConvertUtil.hmacsha256(map, ApiConfig.getToken()));
        enqueueClue(call, true);
    }


}
