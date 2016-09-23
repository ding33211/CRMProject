package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.SearchUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class BusinessOpportunityRvAdapter extends BaseBig4RvAdapter<BusinessOpportunityParams> {

    private Context mContext;

    public BusinessOpportunityRvAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder){
            ((ItemViewHolder) holder).title.setText(mList.get(position).getTitle());
            ((ItemViewHolder) holder).subLine.setVisibility(View.VISIBLE);
            ((ItemViewHolder) holder).subRight.setVisibility(View.VISIBLE);
            if(TextUtils.equals(mList.get(position).getAmountPrice(), "0")){
                ((ItemViewHolder) holder).subLine.setVisibility(View.GONE);
                ((ItemViewHolder) holder).subRight.setVisibility(View.GONE);
            } else {
                ((ItemViewHolder) holder).subRight.setText(mList.get(position).getAmountPrice());
            }
            ((ItemViewHolder) holder).state.setText(getStatusArray()[SearchUtil.searchInArray(getStatusWebArray(), mList.get(position).getStatus())]);
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getCustomer().getName());
        }
    }

    @Override
    int getTypeOf4() {
        return Contants.TYPE_BUSINESS_OPPORTUNITY;
    }

    @Override
    CharSequence[] getStatusWebArray() {
        return mContext.getResources().getStringArray(R.array.business_opportunity_status_web);
    }

    @Override
    CharSequence[] getStatusArray() {
        return mContext.getResources().getStringArray(R.array.business_opportunity_status);
    }
}
