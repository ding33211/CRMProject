package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;

import com.soubu.crmproject.model.ClueParams;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueRvAdapter extends BaseBig4RvAdapter<ClueParams> {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder) {
            ((ItemViewHolder) holder).title.setText(mList.get(position).getCompanyName());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getContactName());
            ((ItemViewHolder) holder).state.setText(mList.get(position).getStatus());
        }
    }

    @Override
    int getTypeOf4() {
        return TYPE_CLUE;
    }
}
