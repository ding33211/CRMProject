package com.soubu.crmproject.delegate;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.HeaderAndFooterRecyclerViewAdapter;
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

    public void addHeaderView(View view){
        LinearLayout contentAll = get(R.id.ll_recycler_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        contentAll.addView(view, 0);
    }
}
