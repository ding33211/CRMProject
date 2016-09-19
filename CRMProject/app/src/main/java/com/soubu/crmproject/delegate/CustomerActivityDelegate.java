package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.CustomerRvAdapter;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivityDelegate extends BaseRecyclerViewActivityDelegate {

    CustomerRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.all_customer);
        mAdapter = new CustomerRvAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
    }


    public void setData(List<CustomerParams> list, boolean isRefresh) {
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());

    }

    public CustomerParams getCustomerParams(int pos){
        return mAdapter.getParams(pos);
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    @Override
    public boolean ifNeedFilterOrSorter() {
        return true;
    }

    @Override
    public boolean ifNeedUseSwipeRefresh() {
        return true;
    }

    @Override
    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack) {
        registerSwipeRefreshCallBack(callBack, mAdapter);
    }

    @Override
    public void setOnRecyclerViewItemClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

}
