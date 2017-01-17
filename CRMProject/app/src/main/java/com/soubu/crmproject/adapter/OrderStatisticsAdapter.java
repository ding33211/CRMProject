package com.soubu.crmproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soubu.crmproject.R;

/**
 * Created by dingsigang on 17-1-16.
 */

public class OrderStatisticsAdapter extends BaseHomePageIndicatorViewPagerAdapter {
    Context context;

    public OrderStatisticsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getViewPagerLayoutRes(), container, false);
        }
        return convertView;
    }

    @Override
    public int getViewPagerLayoutRes() {
        return R.layout.item_order_statistics;
    }

}
