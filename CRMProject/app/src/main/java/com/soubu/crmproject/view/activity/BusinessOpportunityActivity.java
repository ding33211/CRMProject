package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.adapter.BusinessOpportunityRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.BusinessOpportunityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-8-18.
 */
public class BusinessOpportunityActivity extends ActivityPresenter<BusinessOpportunityDelegate> {
    private FilterOrSortPopupWindow mPopupWindow = null;

    private BusinessOpportunityRvAdapter mAdapter;
    private int mPageNum = 1;

    @Override
    protected Class<BusinessOpportunityDelegate> getDelegateClass() {
        return BusinessOpportunityDelegate.class;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ArrayList<ClueParams> list) {
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
