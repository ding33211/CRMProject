package com.soubu.crmproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;

/**
 * Created by dingsigang on 16-8-12.
 */
public class BaseHomePageIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
    Context context;

    public BaseHomePageIndicatorViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.text_home_indicator, container, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_tab);
        if (position == 0) {
            textView.setText(R.string.last_month);
        } else {
            textView.setText(R.string.this_month);
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        return null;
    }
}
