package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.BusinessOpportunityRvAdapter;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class BusinessOpportunityActivityDelegate extends BaseRecyclerViewActivityDelegate {
    BusinessOpportunityRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.all_business_opportunity);
        mAdapter = new BusinessOpportunityRvAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
    }

    public void setData(List<BusinessOpportunityParams> list, boolean isRefresh) {
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public BusinessOpportunityParams getBusinessOpportunityParams(int pos){
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
