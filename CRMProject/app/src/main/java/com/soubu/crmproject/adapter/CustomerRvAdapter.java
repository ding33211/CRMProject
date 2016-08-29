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
public class CustomerRvAdapter extends BaseBig4RvAdapter<CustomerParams> {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseBig4RvAdapter.ItemViewHolder) {
            ((ItemViewHolder) holder).title.setText(mList.get(position).getName());
            ((ItemViewHolder) holder).subTitle.setText(mList.get(position).getManager());
            ((ItemViewHolder) holder).state.setText(mList.get(position).getStatus());
        }
    }

    @Override
    int getTypeOf4() {
        return TYPE_CUSTOMER;
    }
}
