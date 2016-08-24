package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ClueParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueRvAdapter extends BaseWithFooterRvAdapter {

    List<ClueParams> mList;
    OnItemClickListener mListener;


    public ClueRvAdapter() {
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
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).title.setText(mList.get(position).getCompanyName());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getContactName());
            ((ItemViewHolder) holder).state.setText(mList.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return isShowFooter() ? mList.size() + 1 : mList.size();
    }

    public void setData(List<ClueParams> list, boolean isRefresh) {
        if(isRefresh){
            if (!mList.isEmpty()) {
                mList.clear();
            }
        }
        if(list.size() < PAGE_SIZE){
            setShowFooter(false);
        } else {
            setShowFooter(true);
        }
        mList.addAll(list);
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

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ClueParams getClueParams(int pos){
        return mList.get(pos);
    }
}
