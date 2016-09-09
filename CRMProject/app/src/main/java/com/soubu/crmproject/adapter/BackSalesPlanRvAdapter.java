package com.soubu.crmproject.adapter;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.BackSalesParams;

import java.util.List;

/**
 * Created by dingsigang on 16-9-7.
 */
public class BackSalesPlanRvAdapter extends BaseWithFooterRvAdapter<BackSalesParams> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_back_sales_plan_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView price;
        TextView state;

        public ItemViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_sales_back_date);
            price = (TextView) itemView.findViewById(R.id.tv_sales_back_price);
            state = (TextView) itemView.findViewById(R.id.tv_sales_back_state);
        }



    }
}
