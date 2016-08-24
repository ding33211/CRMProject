package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ClueParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class CustomerRvAdapter extends BaseWithFooterRvAdapter{
    List<ClueParams> mList;


    public CustomerRvAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_big_4_recyclerview, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BaseWithFooterRvAdapter.FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<ClueParams> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {


        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
