package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.AllFollowActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;

import java.util.Map;

/**
 * Created by dingsigang on 16-9-28.
 */
public class AllFollowActivity extends Big4AllActivityPresenter<AllFollowActivityDelegate> {
    private int mType = -1;


    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Contants.EXTRA_TYPE, -1);
    }

    @Override
    protected void initView() {
        super.initView();
        if (mType == Contants.TYPE_FOLLOW_RECORD) {
            viewDelegate.setLeftFilterTitle(getString(R.string.follow_object));
            viewDelegate.setRightFilterTitle(getString(R.string.follow_man));
            viewDelegate.setTitle(R.string.follow_record);
        } else {
            viewDelegate.setTitle(R.string.follow_plan);
            viewDelegate.setLeftFilterTitle(getString(R.string.follow_object));
            viewDelegate.setRightFilterTitle(getString(R.string.follow_time));
        }
    }

    @Override
    protected int getParentArray() {
        return R.array.follow_object;
    }

    @Override
    protected String[][] getChildrenArray() {
        return null;
    }

    @Override
    protected int getSortArray() {
        if (mType == Contants.TYPE_FOLLOW_RECORD) {
            return R.array.follow_man;
        } else {
            return R.array.follow_time;
        }
    }

    @Override
    protected void doRequest(int pageNum) {

    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {

    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {

    }

    @Override
    FilterOrSortPopupWindow.SelectCategory getFirstFilterListener() {
        return new FilterOrSortPopupWindow.SelectCategory() {
            @Override
            public void selectTwoStep(Map<Integer, Integer> map) {

            }

            @Override
            public void selectOneStep(int pos) {
                onSelectFollowObject(pos);
            }
        };
    }

    private void onSelectFollowObject(int pos) {
        switch (pos) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    protected void onSelectSort(int pos) {
        switch (pos) {
            case 0:
                break;
            case 1:
                break;
        }
        mIsRefresh = true;
    }

    @Override
    protected void onClickAdd(View v) {
        Intent intent = new Intent(this, AddFollowHomeActivity.class);
        if(mType == Contants.TYPE_FOLLOW_RECORD){
            intent.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_RECORD);
        } else {
            intent.putExtra(Contants.EXTRA_TYPE, Contants.TYPE_FOLLOW_PLAN);
        }
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {

    }

    @Override
    protected Class<AllFollowActivityDelegate> getDelegateClass() {
        return AllFollowActivityDelegate.class;
    }
}
