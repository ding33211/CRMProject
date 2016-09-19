package com.soubu.crmproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.ClueActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
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

    boolean mRushAction = false;

    @Override
    protected Class<ClueActivityDelegate> getDelegateClass() {
        return ClueActivityDelegate.class;
    }


    @Override
    protected int getParentArray() {
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            return R.array.clue_high_seas_filter;
        } else {
            return R.array.clue_filter;
        }
    }

    @Override
    protected String[][] getChildrenArray() {
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            return new String[][]{getResources().getStringArray(R.array.clue_source), getResources().getStringArray(R.array.clue_status)};
        } else {
            return new String[][]{getResources().getStringArray(R.array.clue_source), getResources().getStringArray(R.array.clue_status),
                    getResources().getStringArray(R.array.clue_related)};
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setHighSeas(mFrom == Contants.TYPE_HIGH_SEAS);
    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        if (mRushAction) {
            if (params != null && params.length > 0) {
                final ClueParams params1 = params[0];
                new android.app.AlertDialog.Builder(this).setMessage(getString(R.string.succeed_rush_clue_message, params1.getCompanyName()))
                        .setPositiveButton(R.string.look_clue_spec, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ClueActivity.this, ClueSpecActivity.class);
                                intent.putExtra(Contants.EXTRA_CLUE, params1);
                                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE_HIGH_SEAS);
                                startActivity(intent);
                            }
                        }).setNegativeButton(R.string.continue_rush, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doRequest(1);
                        dialog.dismiss();
                    }
                }).show();
            }
            mRushAction = false;
        } else {
            List<ClueParams> list = Arrays.asList(params);
            Log.e("xxxxxxxxxx", "    refreshData     mIsRefresh   " + mIsRefresh);
            viewDelegate.setData(list, mIsRefresh);
            if (mIsRefresh) {
                mIsRefresh = false;
                viewDelegate.stopSwipeRefresh();
            }
        }
    }

    @Override
    protected void doRequest(int pageNum) {
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            RetrofitRequest.getInstance().getClueHighSeasList(pageNum, mSource, mStatus, mSort, mOrder, null, null);
        } else {
            RetrofitRequest.getInstance().getClueList(pageNum, mSource, mStatus, mSort, mOrder, mRelated, null, null);
        }
    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            Intent intent = new Intent(this, ClueSpecActivity.class);
            intent.putExtra(Contants.EXTRA_CLUE, viewDelegate.getClueParams(pos));
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE_HIGH_SEAS);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ClueActivity.this, ClueHomeActivity.class);
            intent.putExtra(Contants.EXTRA_CLUE, viewDelegate.getClueParams(pos));
            startActivity(intent);
        }
    }

    @Override
    public void onRushClickListener(View v, int pos) {
        RetrofitRequest.getInstance().rushClue(viewDelegate.getClueParams(pos).getId());
        mRushAction = true;
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
                if (map.get(0) == 0) {
                    mSource = null;
                } else {
                    mSource = strings0[map.get(0) - 1];
                }
            }
            if (map.containsKey(1)) {
                if (map.get(1) == 0) {
                    mStatus = null;
                } else {
                    mStatus = strings1[map.get(1) - 1];
                }
            }
            if (map.containsKey(2)) {
                if (map.get(2) == 0) {
                    mRelated = null;
                } else {
                    mRelated = strings2[map.get(2) - 1];
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
        Intent intent = new Intent(ClueActivity.this, AddClueActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {
        Intent intent = new Intent(ClueActivity.this, SearchActivity.class);
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE_HIGH_SEAS);
        } else {
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
        }
        startActivity(intent);
    }


}
