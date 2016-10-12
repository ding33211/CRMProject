package com.soubu.crmproject.view.activity;

import android.util.Log;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.BasePerformanceActivityDelegate;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-10-9.
 */
public class BasePerformanceActivity extends ActivityPresenter<BasePerformanceActivityDelegate> {
    @Override
    protected Class<BasePerformanceActivityDelegate> getDelegateClass() {
        return BasePerformanceActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initView();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            list.add((int)(Math.random() * 1000));
            Log.e("1111111", list.get(i) + "");
        }
        viewDelegate.setLineDataList(list, 200);
        ArrayList<Integer> list2 = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            list2.add((int)(Math.random() * 100));
            Log.e("2222222", list2.get(i) + "");
        }
        viewDelegate.setBarDataList(list2, 20);
    }
}
