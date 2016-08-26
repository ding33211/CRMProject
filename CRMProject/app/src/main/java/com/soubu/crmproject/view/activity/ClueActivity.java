package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.ClueActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueActivity extends Big4AllActivityPresenter<ClueActivityDelegate> {

    String mSource = null;
    String mStatus = null;
    String mSort = null;
    String mOrder = null;
    String mRelated = null;

    @Override
    protected Class<ClueActivityDelegate> getDelegateClass() {
        return ClueActivityDelegate.class;
    }


    @Override
    protected int getParentArray() {
        return R.array.clue_filter;
    }

    @Override
    protected String[][] getChildrenArray() {
        return new String[][]{getResources().getStringArray(R.array.clue_source), getResources().getStringArray(R.array.clue_status),
                getResources().getStringArray(R.array.clue_related)};
    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<ClueParams> list) {
        Log.e("xxxxxxxxxx", "    refreshData     mIsRefresh   " + mIsRefresh);
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
    protected void doRequest(int pageNum) {
        RetrofitRequest.getInstance().getClueList(pageNum, mSource, mStatus, mSort, mOrder, mRelated, null);
    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        Intent intent = new Intent(ClueActivity.this, ClueHomeActivity.class);
        intent.putExtra(Contants.EXTRA_CLUE, viewDelegate.getClueParams(pos));
        startActivity(intent);
    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            mStatus = null;
            mSource = null;
            mRelated = null;
        } else {
            String[] strings0 = getResources().getStringArray(R.array.clue_source_web);
            String[] strings1 = getResources().getStringArray(R.array.clue_status_web);
            String[] strings2 = getResources().getStringArray(R.array.clue_related_web);
            if (map.containsKey(0)) {
                mSource = strings0[map.get(0)];
            }
            if (map.containsKey(1)) {
                mStatus = strings1[map.get(1)];
            }
            if (map.containsKey(2)) {
                mRelated = strings2[map.get(2)];
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
        Intent intent = new Intent(ClueActivity.this, AddClueActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {

    }


}
