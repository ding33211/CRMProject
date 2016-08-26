package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.AddItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-24.
 */
public class ClueSpecRvAdapter extends RecyclerView.Adapter {
    private List<AddItem> mList;

    public ClueSpecRvAdapter() {
        mList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clue_spec_recyclerview, parent, false);
        View vContent = v.findViewById(R.id.rl_item_content);
        View vLabel = v.findViewById(R.id.rl_label);
        View vBottom = v.findViewById(R.id.v_bottom);
        switch (viewType){
            //此处约定,三个type,相应的显式label,中间,和底部item
            case AddSomethingRvAdapter.TYPE_LABEL:
                vContent.setVisibility(View.GONE);
                break;
            case AddSomethingRvAdapter.TYPE_OTHER:
                vBottom.setVisibility(View.VISIBLE);
            case AddSomethingRvAdapter.TYPE_ITEM_UNABLE:
                vLabel.setVisibility(View.GONE);
                break;

        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            switch (holder.getItemViewType()){
                case AddSomethingRvAdapter.TYPE_LABEL:
                    ((ItemViewHolder) holder).tvLabel.setText(mList.get(position).getTitleRes());
                    break;
                case AddSomethingRvAdapter.TYPE_ITEM_UNABLE:
                case AddSomethingRvAdapter.TYPE_OTHER:
                    ((ItemViewHolder) holder).tvTitle.setText(mList.get(position).getTitleRes());
                    ((ItemViewHolder) holder).tvContent.setText(mList.get(position).getContent());
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    public void setData(List<AddItem> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLabel;
        public TextView tvTitle;
        public TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
