package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.adapter.CustomerRvAdapter;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivityDelegate extends BaseRecyclerViewActivityDelegate {

    CustomerRvAdapter mAdapter;
    private boolean mIfHighSeas = false;

    @Override
    public void initWidget() {
        super.initWidget();
        if(mIfHighSeas){
            setTitle(R.string.customer_high_seas);
        } else {
            setTitle(R.string.all_customer);
        }
        mAdapter = new CustomerRvAdapter(getActivity().getApplicationContext(), mIfHighSeas);
        setListAdapter(mAdapter);
    }


    public void setData(List<CustomerParams> list, boolean isRefresh) {
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public void setOnRushClickListener(ClueRvAdapter.OnItemClickListener listener){
        mAdapter.setOnRushClickListener(listener);
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

    public void setHighSeas(boolean flag){
        mIfHighSeas = flag;
    }

}
