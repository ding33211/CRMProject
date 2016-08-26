package com.soubu.crmproject.delegate;

import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.model.AddItem;

import java.util.List;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddSomethingActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private AddSomethingRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new AddSomethingRvAdapter(getActivity());
        setListAdapter(mAdapter);
    }

    public void setData(List<AddItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    public List<AddItem> getData(){
        return mAdapter.getData();
    }

    public boolean verifyRequired(){
        return mAdapter.verifyRequired();
    }

}
