package com.soubu.crmproject.delegate;

import com.soubu.crmproject.adapter.SpecRvAdapter;
import com.soubu.crmproject.model.AddItem;

import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class SpecActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private SpecRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new SpecRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<AddItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
