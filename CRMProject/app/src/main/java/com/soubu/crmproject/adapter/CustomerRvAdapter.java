package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.utils.SearchUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class CustomerRvAdapter extends BaseBig4RvAdapter<CustomerParams> {

    private Context mContext;
    private CharSequence[] mTypeArray;
    private CharSequence[] mTypeWebArray;
    private CharSequence[] mPropertyArray;
    private CharSequence[] mPropertyWebArray;

    public CustomerRvAdapter(Context context) {
        mContext = context;
        mTypeArray = SearchUtil.searchCustomerTypeArray(context);
        mTypeWebArray = SearchUtil.searchCustomerTypeWebArray(context);
        mPropertyArray = SearchUtil.searchCustomerPropertyArray(context);
        mPropertyWebArray = SearchUtil.searchCustomerPropertyWebArray(context);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder) {
            ((ItemViewHolder) holder).title.setText(mList.get(position).getName());
            ((ItemViewHolder) holder).subTitle.setText(mPropertyArray[SearchUtil.searchInArray(mPropertyWebArray, mList.get(position).getProperty())]);
            ((ItemViewHolder) holder).subRight.setText(mTypeArray[SearchUtil.searchInArray(mTypeWebArray, mList.get(position).getType())]);
            ((ItemViewHolder) holder).state.setText(getStatusArray()[SearchUtil.searchInArray(getStatusWebArray(), mList.get(position).getStatus())]);
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
