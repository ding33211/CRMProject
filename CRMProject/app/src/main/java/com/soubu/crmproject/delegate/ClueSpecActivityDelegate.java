package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueSpecRvAdapter;
import com.soubu.crmproject.model.AddItem;

import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class ClueSpecActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private ClueSpecRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.clue_spec);
        mAdapter = new ClueSpecRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<AddItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }
}
