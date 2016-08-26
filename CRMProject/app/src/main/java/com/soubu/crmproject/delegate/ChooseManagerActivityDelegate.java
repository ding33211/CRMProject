package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.adapter.ChooseManagerRvAdapter;
import com.soubu.crmproject.model.AddItem;

import java.util.List;

/**
 * Created by dingsigang on 16-8-25.
 */
public class ChooseManagerActivityDelegate extends BaseRecyclerViewActivityDelegate {
    ChooseManagerRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.add_clue);
        mAdapter = new ChooseManagerRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<String> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
