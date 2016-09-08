package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AlreadyBackSalesRvAdapter;
import com.soubu.crmproject.model.ClueParams;

import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class AlreadyBackSalesActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private AlreadyBackSalesRvAdapter mAdapter;


    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.already_sales_back);
        mAdapter = new AlreadyBackSalesRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<ClueParams> list, boolean isRefresh){
//        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
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
