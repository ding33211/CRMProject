package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ApprovalActivityDelegate;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

/**
 * Created by dingsigang on 16-9-14.
 */
public class ApprovalActivity extends ActivityPresenter<ApprovalActivityDelegate> {
    boolean mIsRefresh = false;
    int mPageNum = 1;

    @Override
    protected Class<ApprovalActivityDelegate> getDelegateClass() {
        return ApprovalActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.registerSwipeRefreshCallBack(new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true);
            }

            @Override
            public void loadMore() {
                getList(false);
            }
        });
    }

    private void getList(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        doRequest(mPageNum);
    }

    private void doRequest(int pageNum) {
//        RetrofitRequest.getInstance().getContractList(pageNum, mType, mPayMethod, mStatus, mReceivedPayType, mSort, mOrder, mRelated, null, null);
    }


}
