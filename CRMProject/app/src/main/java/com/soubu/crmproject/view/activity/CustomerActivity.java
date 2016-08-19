package com.soubu.crmproject.view.activity;

import android.util.Log;

import com.soubu.crmproject.adapter.CustomerRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.CustomerActivityDelegate;
import com.soubu.crmproject.model.ClueTest;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivity extends ActivityPresenter<CustomerActivityDelegate> {

    private FilterOrSortPopupWindow mPopupWindow = null;

    private CustomerRvAdapter mAdapter;
    private int mPageNum = 1;

    @Override
    protected Class<CustomerActivityDelegate> getDelegateClass() {
        return CustomerActivityDelegate.class;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ArrayList<ClueTest> list){
        Log.e("xxxxxxxxxx", list.size() + "");
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 请求clue失败
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t){

    }
}
