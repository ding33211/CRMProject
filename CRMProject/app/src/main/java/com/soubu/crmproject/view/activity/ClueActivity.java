package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ClueActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.widget.FilterOrSortPopupWindow;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueActivity extends ActivityPresenter<ClueActivityDelegate> {
    private String[] parentStrings = {"线索来源", "线索状态"};
    private String[][] childrenStrings = {{"广告", "社交推广", "研讨会", "搜索引擎", "客户介绍", "独立开发", "代理商", "其他"},
//            {"中原1", "中原2", "中原3", "中原4", "中原5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
//            {"二七1", "二七2", "二七3", "二七4", "二七5", "二七6", "二七7", "二七8", "二七9", "二七10", "二七11", "二七12", "二七13", "二七14", "二七15"},
//            {"管城1", "管城2", "管城3", "管城4", "管城5", "管城6", "管城7", "管城8", "管城9", "管城10", "管城11", "管城12", "管城13", "管城14", "管城15"},
//            {"金水1", "金水2", "金水3", "金水4", "金水5", "金水6", "金水7", "金水8", "金水9", "金水10", "金水11", "金水12", "金水13", "金水14", "金水15"},
//            {"上街1", "上街2", "上街3", "上街4", "上街5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
//            {"中原1", "中原2", "中原3", "中原4", "中原5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
//            {"郑东新区1", "郑东新区2", "郑东新区3", "中原4", "中原5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
//            {"高新区1", "高新区2", "高新区3", "中原4", "中原5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
//            {"经开区1", "经开区2", "经开区3", "中原4", "中原5", "中原6", "中原7", "中原8", "中原9", "中原10", "中原11", "中原12", "中原13", "中原14", "中原15"},
            {"未处理", "联系方式有效", "联系方式无效", "关闭"}};
    private String[] sortStrings = {"时间排序", "姓名排序"};

    private int mPageNum = 1;

    private boolean mIsRefresh = false;

    @Override
    protected Class<ClueActivityDelegate> getDelegateClass() {
        return ClueActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        getClueList(true);
//        viewDelegate.setData();
//        viewDelegate.setFilters(mFilter1, mFilter2, mFilter3, mFilter4);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.bindFilterAndSortEventListener(this, parentStrings, childrenStrings, null, sortStrings, new FilterOrSortPopupWindow.SelectCategory() {
            @Override
            public void selectCategory(int parentSelectPosition, int childrenSelectPosition, int grandChildSelectPosition) {

            }
        }, new FilterOrSortPopupWindow.SelectCategory() {
            @Override
            public void selectCategory(int parentSelectPosition, int childrenSelectPosition, int grandChildSelectPosition) {

            }
        });
        viewDelegate.registerSwipeRefreshCallBack(new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getClueList(true);
            }

            @Override
            public void loadMore() {
                getClueList(false);
            }
        });
        viewDelegate.setOnRecyclerViewItemClickListener(new ClueRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(ClueActivity.this, ClueHomeActivity.class);
                intent.putExtra("clue", viewDelegate.getClueParams(pos));
                startActivity(intent);

            }
        });
    }


    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<ClueParams> list) {
        if(mIsRefresh){
            viewDelegate.stopSwipeRefresh();
        }
        viewDelegate.setData(list, mIsRefresh);
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }


    /**
     * 从网络加载数据列表
     *
     * @param isRefresh 是否刷新
     */
    private void getClueList(final boolean isRefresh) {
//        viewDelegate.showLoading();
        mIsRefresh = isRefresh;
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        RetrofitRequest.getClueList(0, null, null);
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClueActivity.this, AddClueActivity.class);
                startActivity(intent);
            }
        });
        viewDelegate.setRightMenuTwo(R.drawable.btn_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
