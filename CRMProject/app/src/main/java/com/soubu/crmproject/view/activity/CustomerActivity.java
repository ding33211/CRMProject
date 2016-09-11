package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.CustomerActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivity extends Big4AllActivityPresenter<CustomerActivityDelegate> {

    String mSource = null;
    String mStatus = null;
    String mType = null;
    String mSize = null;
    String mIndustry = null;
    String mSort = null;
    String mOrder = null;
    String mRelated = null;


    @Override
    protected Class<CustomerActivityDelegate> getDelegateClass() {
        return CustomerActivityDelegate.class;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        List<CustomerParams> list = Arrays.asList(params);
        viewDelegate.setData(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopSwipeRefresh();
        }
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }

    @Override
    protected int getParentArray() {
        return R.array.customer_filter;
    }

    @Override
    protected String[][] getChildrenArray() {
        return new String[][]{getResources().getStringArray(R.array.customer_type),
                getResources().getStringArray(R.array.clue_source), getResources().getStringArray(R.array.customer_size),
                getResources().getStringArray(R.array.customer_industry), getResources().getStringArray(R.array.customer_status),
                getResources().getStringArray(R.array.clue_related)};
    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    @Override
    protected void doRequest(int pageNum) {
        RetrofitRequest.getInstance().getCustomerList(pageNum, mType, mSource, mSize, mIndustry, mStatus, mSort, mOrder, mRelated, null);

    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        CustomerParams params = viewDelegate.getCustomerParams(pos);
        if(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY){
            Intent intent = new Intent();
            intent.putExtra(Contants.EXTRA_CUSTOMER_ID, params.getId());
            intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, params.getName());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(this, CustomerHomeActivity.class);
            intent.putExtra(Contants.EXTRA_CUSTOMER, params);
            startActivity(intent);
        }

    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            mSource = null;
            mStatus = null;
            mType = null;
            mSize = null;
            mIndustry = null;
        } else {
            String[] strings0 = getResources().getStringArray(R.array.customer_type_web);
            String[] strings1 = getResources().getStringArray(R.array.clue_source_web);
            String[] strings2 = getResources().getStringArray(R.array.customer_size_web);
            String[] strings3 = getResources().getStringArray(R.array.customer_industry_web);
            String[] strings4 = getResources().getStringArray(R.array.customer_status_web);
            String[] strings5 = getResources().getStringArray(R.array.clue_related_web);

            if (map.containsKey(0)) {
                if(map.get(0) == 0){
                    mType = null;
                } else {
                    mType = strings0[map.get(0) - 1];
                }
            }
            if (map.containsKey(1)) {
                if(map.get(1) == 0){
                    mSource = null;
                } else {
                    mSource = strings1[map.get(1) - 1];
                }
            }
            if (map.containsKey(2)) {
                if(map.get(2) == 0){
                    mSize = null;
                } else {
                    mSize = strings2[map.get(2) - 1];
                }
            }
            if (map.containsKey(3)) {
                if(map.get(3) == 0){
                    mIndustry = null;
                } else {
                    mIndustry = strings3[map.get(3) - 1];
                }
            }
            if (map.containsKey(4)) {
                if(map.get(4) == 0){
                    mStatus = null;
                } else {
                    mStatus = strings4[map.get(4) - 1];
                }
            }
            if(map.containsKey(5)){
                if(map.get(5) == 0){
                    mRelated = null;
                } else {
                    mRelated = strings5[map.get(5) - 1];
                }
            }
        }
        mIsRefresh = true;
        doRequest(1);
    }

    @Override
    protected void onSelectSort(int pos) {
        switch (pos) {
            case 0:
                mSort = "CREATED_AT";
                mOrder = "ASC";
                break;
            case 1:
                mSort = "CREATED_AT";
                mOrder = "DESC";
                break;
            case 2:
                mSort = "UPDATED_AT";
                mOrder = "ASC";
                break;
            case 3:
                mSort = "CREATED_AT";
                mOrder = "DESC";
                break;
        }
        mIsRefresh = true;
        doRequest(1);
    }

    @Override
    protected void onClickAdd(View v) {
        Intent intent = new Intent(this, AddCustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {

    }

    @Override
    protected int getFrom() {
        return mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, -1);
    }
}
