package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.delegate.BusinessOpportunityActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-18.
 */
public class BusinessOpportunityActivity extends Big4AllActivityPresenter<BusinessOpportunityActivityDelegate> {

    String mSource = null;
    String mStatus = null;
    String mType = null;
    String mSort = null;
    String mOrder = null;
    String mRelated = null;
    String mCustomerId = null;
    String mCustomerName = null;

    @Override
    protected Class<BusinessOpportunityActivityDelegate> getDelegateClass() {
        return BusinessOpportunityActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        mCustomerId = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID);
        mCustomerName = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
        if (!TextUtils.isEmpty(mCustomerId)) {
            viewDelegate.setTitle(R.string.business_opportunity);
        } else {
            viewDelegate.setTitle(R.string.all_business_opportunity);
        }
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(BusinessOpportunityParams[] params) {
        List<BusinessOpportunityParams> list = new ArrayList<>(Arrays.asList(params));
        if (mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY) {
            //如果是从新增合同过来的，那么商机只显示没有转成合同的供选择
            String transfer = SearchUtil.searchBusinessOpportunityStateWebArray(getApplicationContext())[6].toString();
            for (int i = 0; i < list.size(); i++) {
                if (TextUtils.equals(list.get(i).getStatus(), transfer)) {
                    Log.e("xxxxxxxsize", list.size() + "    i :   " + i);
                    list.remove(i);
                    i--;
                }
            }
        }
        viewDelegate.setData(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopSwipeRefresh();
        }
    }


    @Override
    protected int getParentArray() {
        return R.array.business_opportunity_filter;
    }

    @Override
    protected String[][] getChildrenArray() {
        return new String[][]{getResources().getStringArray(R.array.business_opportunity_type),
                getResources().getStringArray(R.array.clue_source),
                getResources().getStringArray(mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY ? R.array.business_opportunity_status_without_transfer : R.array.business_opportunity_status),
                getResources().getStringArray(R.array.clue_related)};
    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    @Override
    protected void doRequest(int pageNum) {
        RetrofitRequest.getInstance().getBusinessOpportunityList(pageNum, mType, mSource, mStatus, mSort, mOrder, mRelated, null, mCustomerId, null);
    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        BusinessOpportunityParams params = viewDelegate.getBusinessOpportunityParams(pos);
        if (mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY) {
            Intent intent = new Intent();
            intent.putExtra(Contants.EXTRA_BUSINESS_ID, params.getId());
            intent.putExtra(Contants.EXTRA_BUSINESS_NAME, params.getTitle());
            intent.putExtra(Contants.EXTRA_CUSTOMER_ID, params.getCustomer().getId());
            intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, params.getCustomer().getName());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(BusinessOpportunityActivity.this, BusinessOpportunityHomeActivity.class);
            intent.putExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY, viewDelegate.getBusinessOpportunityParams(pos));
            startActivity(intent);
        }
    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            mStatus = null;
            mSource = null;
            mRelated = null;
            mType = null;
        } else {
            String[] strings0 = getResources().getStringArray(R.array.business_opportunity_type_web);
            String[] strings1 = getResources().getStringArray(R.array.clue_source_web);
            String[] strings2 = getResources().getStringArray(R.array.business_opportunity_status_web);
            String[] strings3 = getResources().getStringArray(R.array.clue_related_web);
            if (map.containsKey(0)) {
                if (map.get(0) == 0) {
                    mType = null;
                } else {
                    mType = strings0[map.get(0) - 1];
                }
            }
            if (map.containsKey(1)) {
                if (map.get(1) == 0) {
                    mSource = null;
                } else {
                    mSource = strings1[map.get(1) - 1];
                }
            }
            if (map.containsKey(2)) {
                if (map.get(2) == 0) {
                    mStatus = null;
                } else {
                    mStatus = strings2[map.get(2) - 1];
                }
            }
            if (map.containsKey(3)) {
                if (map.get(3) == 0) {
                    mRelated = null;
                } else {
                    mRelated = strings3[map.get(3) - 1];
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
        Intent intent = new Intent(this, AddBusinessOpportunityActivity.class);
        if (!TextUtils.isEmpty(mCustomerId)) {
            intent.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerId);
        }
        if (!TextUtils.isEmpty(mCustomerName)) {
            intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, mCustomerName);
        }
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_BUSINESS_OPPORTUNITY);
        startActivity(intent);
    }
}
