package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.R;
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
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            test.add(i + 1 + "月");
        }
        viewDelegate.setBottomTextList(test);
//        ArrayList<Integer> list = new ArrayList<>();
//        for(int i = 0; i < 12; i++){
//            list.add((int)(Math.random() * 1000));
//            Log.e("1111111", list.get(i) + "");
//        }
//        ArrayList<ArrayList<Integer>> lineList = new ArrayList<>();
//        lineList.add(list);
//        viewDelegate.setLineDataList(lineList, 200);
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            list2.add((int)(Math.random() * 100));
            list3.add((int)(Math.random() * 100));
        }
        ArrayList<ArrayList<Integer>> barList = new ArrayList<>();
        barList.add(list2);
        barList.add(list3);
        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(getResources().getColor(R.color.progressbar_green));
        colorList.add(getResources().getColor(R.color.colorPrimary));
        viewDelegate.setBarDataList(barList, 20, colorList);
    }
}
