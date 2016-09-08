package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.BackSalesParams;
import com.soubu.crmproject.model.ContactParams;

import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class AlreadyBackSalesRvAdapter extends BaseWithFooterRvAdapter<BackSalesParams> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM || viewType == TYPE_LAST_ONE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_already_back_sales_recyclerview, parent, false);
            if(viewType == TYPE_LAST_ONE){
                v.findViewById(R.id.v_line).setVisibility(View.INVISIBLE);
            }
            return new ItemViewHolder(v);
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

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView price;
        TextView method;

        public ItemViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_sales_back_date);
            price = (TextView) itemView.findViewById(R.id.tv_sales_back_price);
            method = (TextView) itemView.findViewById(R.id.tv_sales_back_method);
        }

    }
}
