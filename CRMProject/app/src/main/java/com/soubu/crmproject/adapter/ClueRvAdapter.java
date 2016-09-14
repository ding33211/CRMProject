package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.SearchUtil;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueRvAdapter extends BaseBig4RvAdapter<ClueParams> {
    private Context mContext;

    public ClueRvAdapter(Context context, Boolean ifHighSeas) {
        mContext = context;
        mIfHighSeas = ifHighSeas;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder) {
            ((ItemViewHolder) holder).title.setText(mList.get(position).getCompanyName());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getContactName());
            ((ItemViewHolder) holder).state.setText(getStatusArray()[SearchUtil.searchInArray(getStatusWebArray(), mList.get(position).getStatus())]);
        }
    }

    @Override
    int getTypeOf4() {
        return Contants.TYPE_CLUE;
    }

    @Override
    CharSequence[] getStatusWebArray() {
        return mContext.getResources().getStringArray(R.array.clue_status_web);
    }

    @Override
    CharSequence[] getStatusArray() {
        return mContext.getResources().getStringArray(R.array.clue_status);
    }
}
