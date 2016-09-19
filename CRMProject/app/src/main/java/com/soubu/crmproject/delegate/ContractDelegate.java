package com.soubu.crmproject.delegate;

import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.ContractRvAdapter;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class ContractDelegate extends BaseRecyclerViewActivityDelegate {
    ContractRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new ContractRvAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
    }

    public void setData(List<ContractParams> list, boolean isRefresh) {
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public ContractParams getContractParams(int pos){
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
