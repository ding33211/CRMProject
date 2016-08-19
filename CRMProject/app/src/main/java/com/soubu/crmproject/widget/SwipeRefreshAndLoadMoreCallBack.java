package com.soubu.crmproject.widget;

/**
 * SwipeRefresh上拉和下拉接口
 * Created by dingsigang on 16-8-17.
 */
public interface SwipeRefreshAndLoadMoreCallBack {

    /**
     * 下拉刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();
}
