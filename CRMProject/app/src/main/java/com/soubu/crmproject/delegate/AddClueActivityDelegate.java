package com.soubu.crmproject.delegate;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.model.AddItem;

import java.util.List;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddClueActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private AddSomethingRvAdapter mAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.add_clue);
        mAdapter = new AddSomethingRvAdapter(getActivity());
        setListAdapter(mAdapter);
    }

    public void setData(List<AddItem> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

//    public RecyclerView getRecyclerView(){
//        return mRvContent;
//    }
//
//    public void setOnItemClickListener(AddSomethingRvAdapter.OnRecyclerViewItemClickListener listener){
//        mAdapter.setOnItemClickListener(listener);
//    }



}
