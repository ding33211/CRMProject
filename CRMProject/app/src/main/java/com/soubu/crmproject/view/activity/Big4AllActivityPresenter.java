package com.soubu.crmproject.view.activity;

import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.BaseRecyclerViewActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

/**
 * 四大模块主页的通用presenter
 * Created by dingsigang on 16-8-26.
 */
public abstract class Big4AllActivityPresenter<T extends BaseRecyclerViewActivityDelegate> extends ActivityPresenter<T> {

    String[] parentStrings;
    String[][] childrenStrings;
    String[] sortStrings;
    boolean mIsRefresh = false;
    int mPageNum = 1;
    int mFrom = -1;


    @Override
    protected void initData() {
        super.initData();
    }

    protected int getFrom() {
        return mFrom;
    }

    protected abstract int getParentArray();

    protected abstract String[][] getChildrenArray();

    protected abstract int getSortArray();

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

        viewDelegate.setOnRecyclerViewItemClickListener(new ClueRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                onRvItemClickListener(v, pos);
            }
        });

        viewDelegate.setOnRushClickListener(new ClueRvAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int pos) {
                onRushClickListener(v, pos);
            }
        });

        parentStrings = getResources().getStringArray(getParentArray());
        childrenStrings = getChildrenArray();
        sortStrings = getResources().getStringArray(getSortArray());
        viewDelegate.bindFilterAndSortEventListener(this, parentStrings, childrenStrings, null, sortStrings, getFirstFilterListener(), getSecondFilterListener());
    }

    FilterOrSortPopupWindow.SelectCategory getFirstFilterListener() {
        return new FilterOrSortPopupWindow.SelectCategory() {
            @Override
            public void selectTwoStep(Map<Integer, Integer> map) {
                onSelectFilter(map);
            }

            @Override
            public void selectOneStep(int pos) {
            }
        };
    }

    FilterOrSortPopupWindow.SelectCategory getSecondFilterListener() {
        return new FilterOrSortPopupWindow.SelectCategory() {
            @Override
            public void selectTwoStep(Map<Integer, Integer> map) {
            }

            @Override
            public void selectOneStep(int pos) {
                onSelectSort(pos);
            }
        };
    }


    void getList(boolean isRefresh) {
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        doRequest(mPageNum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDelegate.startSwipeRefresh();
        getList(true);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, -1);
        if (getFrom() == -1) {
            viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickAdd(v);
                }
            });
        }
        viewDelegate.setRightMenuTwo(R.drawable.btn_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch(v);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        if (!mEventBusJustForThis) {
            return;
        } else {
            mEventBusJustForThis = false;
        }
        ServerErrorUtil.handleServerError(errorCode);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopSwipeRefresh();
        }
    }

    protected abstract void doRequest(int pageNum);

    protected abstract void onRvItemClickListener(View v, int pos);

    public void onRushClickListener(View v, int pos) {
    }

    protected abstract void onSelectFilter(Map<Integer, Integer> map);

    protected abstract void onSelectSort(int pos);

    protected abstract void onClickAdd(View v);

    protected abstract void onClickSearch(View v);
}
