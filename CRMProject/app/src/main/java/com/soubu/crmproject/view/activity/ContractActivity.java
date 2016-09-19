package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.ContractDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-18.
 */
public class ContractActivity extends Big4AllActivityPresenter<ContractDelegate> {
    String mStatus = null;
    String mType = null;
    String mPayMethod = null;
    String mReceivedPayType = null;
    String mReviewState = null;
    String mSort = null;
    String mOrder = null;
    String mRelated = null;

    //是否是审批
    boolean mIfApproval = false;

    @Override
    protected Class<ContractDelegate> getDelegateClass() {
        return ContractDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mIfApproval = getIntent().getIntExtra(Contants.EXTRA_FROM, -1) == Contants.FROM_CONTRACT_APPROVAL;
        if(mIfApproval){
            viewDelegate.setTitle(R.string.contract_approval);
        } else {
            viewDelegate.setTitle(R.string.all_contract);
        }
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        viewDelegate.setData(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopSwipeRefresh();
        }
    }

    @Override
    protected int getParentArray() {
        return R.array.contract_filter;
    }

    @Override
    protected String[][] getChildrenArray() {
        return new String[][]{getResources().getStringArray(R.array.contract_type), getResources().getStringArray(R.array.contract_pay_method),
                getResources().getStringArray(R.array.contract_state), getResources().getStringArray(R.array.contract_review_state),
                getResources().getStringArray(R.array.contract_received_pay_method), getResources().getStringArray(R.array.clue_related)};
    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    @Override
    protected void doRequest(int pageNum) {
        if(mIfApproval){
            RetrofitRequest.getInstance().getContractList(pageNum, mType, mPayMethod, mStatus, mReceivedPayType, "NOT_STARTED", mSort, mOrder, mRelated, null, null);
        } else {
            RetrofitRequest.getInstance().getContractList(pageNum, mType, mPayMethod, mStatus, mReceivedPayType, mReviewState, mSort, mOrder, mRelated, null, null);
        }
    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        if(mIfApproval){

        } else {
            Intent intent = new Intent(this, ContractHomeActivity.class);
            intent.putExtra(Contants.EXTRA_CONTRACT, viewDelegate.getContractParams(pos));
            startActivity(intent);
        }
    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            mStatus = null;
            mRelated = null;
            mType = null;
            mPayMethod = null;
            mReceivedPayType = null;
            mReviewState = null;
        } else {
            String[] strings0 = getResources().getStringArray(R.array.contract_type_web);
            String[] strings1 = getResources().getStringArray(R.array.contract_pay_method_web);
            String[] strings2 = getResources().getStringArray(R.array.contract_state_web);
            String[] strings3 = getResources().getStringArray(R.array.contract_review_state_web);
            String[] strings4 = getResources().getStringArray(R.array.contract_received_pay_method_web);
            String[] strings5 = getResources().getStringArray(R.array.clue_related_web);
            if (map.containsKey(0)) {
                if (map.get(0) == 0) {
                    mType = null;
                } else {
                    mType = strings0[map.get(0) - 1];
                }
            }
            if (map.containsKey(1)) {
                if (map.get(1) == 0) {
                    mPayMethod = null;
                } else {
                    mPayMethod = strings1[map.get(1) - 1];
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
                    mReviewState = null;
                } else {
                    mReviewState = strings3[map.get(3) - 1];
                }
            }
            if (map.containsKey(4)) {
                if (map.get(4) == 0) {
                    mReceivedPayType = null;
                } else {
                    mReceivedPayType = strings4[map.get(4) - 1];
                }
            }
            if (map.containsKey(5)) {
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
        Intent intent = new Intent(this, AddContractActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CONTRACT);
        startActivity(intent);
    }
}
