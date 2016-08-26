package com.soubu.crmproject.delegate;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.WindowUtil;
import com.soubu.crmproject.widget.DividerItemDecoration;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

/**
 * 带有筛选和排序的嵌套有recyclerView的swipeRefreshLayout
 * Created by dingsigang on 16-8-17.
 */
public abstract class BaseRecyclerViewActivityDelegate extends AppDelegate {

    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager mRecycleViewLayoutManager;

    private FilterOrSortPopupWindow mFilterPopupWindow = null;
    private FilterOrSortPopupWindow mSortPopupWindow = null;

    RecyclerView mRvContent;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_base_recycler_view;
    }

    private void initRecyclerView(){
        mRvContent = get(R.id.rv_content);
        mRecycleViewLayoutManager = new LinearLayoutManager(getActivity());
        mRvContent.setLayoutManager(mRecycleViewLayoutManager);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if(ifNeedUseSwipeRefresh()){
            initSwipeRefreshLayout();
        } else {
            get(R.id.srl_container).setEnabled(false);
        }
        initRecyclerView();
        if(!ifNeedFilterOrSorter()){
            get(R.id.ll_top).setVisibility(View.GONE);
            get(R.id.v_line).setVisibility(View.GONE);
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = get(R.id.srl_container);
        mSwipeRefreshLayout.setColorSchemeResources(Contants.colors);
    }

    public void setListAdapter(RecyclerView.Adapter adapter) {
        mRvContent.setAdapter(adapter);
    }



    /**
     * 设置刷新接口
     *
     * @param callBack 刷新的回调接口
     */
    public void registerSwipeRefreshCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final BaseWithFooterRvAdapter adapter) {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callBack.refresh();
            }
        });

        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mRecycleViewLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount() && adapter.isShowFooter()) {
                    //加载更多
                    callBack.loadMore();
                }
            }
        });
    }


    public void bindFilterAndSortEventListener(final Activity activity, final String[] parentStrings,
                                               final String[][] childrenStrings, final String[][][] grandChildStrings,
                                               final String[] sortStrings, final FilterOrSortPopupWindow.SelectCategory filterListener){
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if(id == R.id.ll_filter){
                    ImageView ivArrow = (ImageView)v.findViewById(R.id.iv_filter1);
                    ivArrow.setImageResource(R.drawable.arrow_up);
                    if(mFilterPopupWindow == null){
                        mFilterPopupWindow = new FilterOrSortPopupWindow(parentStrings, childrenStrings, null, activity, filterListener);
                    }
                    mFilterPopupWindow.showAsDropDown(v, 0, 0);
                    bindOnDismissListener(mFilterPopupWindow, ivArrow);
                } else {
                    ImageView ivArrow = (ImageView)v.findViewById(R.id.iv_filter2);
                    ivArrow.setImageResource(R.drawable.arrow_up);
                    if(mSortPopupWindow == null){
                        mSortPopupWindow = new FilterOrSortPopupWindow(sortStrings, null, null, activity, filterListener);
                    }
                    mSortPopupWindow.showAsDropDown(v, 0, 0);
                    bindOnDismissListener(mSortPopupWindow, ivArrow);
                }
            }
        }, R.id.ll_filter, R.id.ll_sort);
    }


    private void bindOnDismissListener(PopupWindow window, final ImageView v){
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                v.setImageResource(R.drawable.arrow_down);
            }
        });
    }

    //默认不需要筛选和排序bar
    public boolean ifNeedFilterOrSorter(){
        return false;
    }

    //默认不需要用到下拉刷新
    public boolean ifNeedUseSwipeRefresh(){
        return false;
    }

    public void stopSwipeRefresh(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack){
        //big4的共有方法
    }

    public  void setOnRecyclerViewItemClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        //big4的共有方法
    }
}
