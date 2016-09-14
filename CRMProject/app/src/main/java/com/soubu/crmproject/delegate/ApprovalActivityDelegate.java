package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ContractRvAdapter;
import com.soubu.crmproject.model.ContractParams;

import java.util.List;

/**
 * Created by dingsigang on 16-9-14.
 */
public class ApprovalActivityDelegate extends BaseRecyclerViewActivityDelegate {
    ContractRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.contract_approval);
        mAdapter = new ContractRvAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
    }

    public void setData(List<ContractParams> list, boolean isRefresh) {
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

}
