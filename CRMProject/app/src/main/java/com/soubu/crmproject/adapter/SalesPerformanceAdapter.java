package com.soubu.crmproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.crmproject.R;
import com.soubu.crmproject.view.activity.BasePerformanceActivity;

/**
 * Created by dingsigang on 16-8-12.
 */
public class SalesPerformanceAdapter extends BaseHomePageIndicatorViewPagerAdapter {
    Context context;

    public SalesPerformanceAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getViewPagerLayoutRes(), container, false);
        }
        convertView.findViewById(R.id.rl_total_turnover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BasePerformanceActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getViewPagerLayoutRes() {
        return R.layout.item_sales_performance;
    }
}
