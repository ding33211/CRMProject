package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.utils.SearchUtil;

/**
 * Created by dingsigang on 16-8-18.
 */
public class CustomerRvAdapter extends BaseBig4RvAdapter<CustomerParams> {

    private Context mContext;
    private CharSequence[] mTypeArray;
    private CharSequence[] mTypeWebArray;
    private CharSequence[] mPropertyArray;
    private CharSequence[] mPropertyWebArray;

    public CustomerRvAdapter(Context context, Boolean ifHighSeas) {
        mContext = context;
        mIfHighSeas = ifHighSeas;
        mTypeArray = SearchUtil.searchCustomerTypeArray(context);
        mTypeWebArray = SearchUtil.searchCustomerTypeWebArray(context);
        mPropertyArray = SearchUtil.searchCustomerPropertyArray(context);
        mPropertyWebArray = SearchUtil.searchCustomerPropertyWebArray(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder) {
            CustomerParams params = mList.get(position);
            ((ItemViewHolder) holder).title.setText(params.getName());
            int property = SearchUtil.searchInArray(mPropertyWebArray, params.getProperty());
            if(TextUtils.isEmpty(params.getContactName())){
                ((ItemViewHolder) holder).vSubRight.setVisibility(View.GONE);
                ((ItemViewHolder) holder).subTitle.setText(params.getAddress());
            } else {
                ((ItemViewHolder) holder).subTitle.setText(params.getContactName());
                ((ItemViewHolder) holder).subRight.setText(params.getAddress());
            }
            if (property == 0) {
                ((ItemViewHolder) holder).tvOrderCount.setText(params.getSellOrdersCount());
                ((ItemViewHolder) holder).tvOrderTurnOver.setText(params.getSellOrdersAmount());
            } else {
                ((ItemViewHolder) holder).tvOrderCount.setText(params.getBuyOrdersCount());
                ((ItemViewHolder) holder).tvOrderTurnOver.setText(params.getBuyOrdersAmount());
            }
            ((ItemViewHolder) holder).tvUsefulFollow.setText(params.getRecordsCount());
//            ((ItemViewHolder) holder).state.setText(getStatusArray()[SearchUtil.searchInArray(getStatusWebArray(), mList.get(position).getStatus())]);
        }
    }

    @Override
    int getTypeOf4() {
        return Contants.TYPE_CUSTOMER;
    }

    @Override
    CharSequence[] getStatusWebArray() {
        return mContext.getResources().getStringArray(R.array.customer_status_web);
    }

    @Override
    CharSequence[] getStatusArray() {
        return mContext.getResources().getStringArray(R.array.customer_status);
    }
}
