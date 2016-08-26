package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.CustomerParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class CustomerRvAdapter extends BaseWithFooterRvAdapter<CustomerParams>{

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
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).title.setText(mList.get(position).getName());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getManager());
            ((ItemViewHolder) holder).state.setText(mList.get(position).getStatus());
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView subTitle;
        TextView state;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            subTitle = (TextView) itemView.findViewById(R.id.tv_desc);
            state = (TextView) itemView.findViewById(R.id.tv_state);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
