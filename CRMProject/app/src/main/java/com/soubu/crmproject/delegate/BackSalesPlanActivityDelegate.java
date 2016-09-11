package com.soubu.crmproject.delegate;

import android.support.v7.widget.LinearLayoutManager;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BackSalesPlanRvAdapter;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by dingsigang on 16-9-7.
 */
public class BackSalesPlanActivityDelegate extends BaseRecyclerViewActivityDelegate {

    private BackSalesPlanRvAdapter mAdapter;


    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.sales_back_plan);
        mAdapter = new BackSalesPlanRvAdapter();
        setListAdapter(mAdapter);
        mRvContent.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 20));
    }

    public void setData(List<ClueParams> list, boolean isRefresh){
//        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());

    }

    @Override
    public boolean ifNeedEventBus() {
        return false;
    }

    @Override
    public boolean ifNeedFilterOrSorter() {
        return false;
    }

    @Override
    public boolean ifNeedUseSwipeRefresh() {
        return true;
    }
}
