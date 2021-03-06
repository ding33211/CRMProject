package com.soubu.crmproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;

/**
 * Created by dingsigang on 16-8-11.
 */
public class WorkPlanAdapter extends BaseHomePageIndicatorViewPagerAdapter {

    Context context;

    public WorkPlanAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.text_home_indicator, container, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_tab);
        if (position == 0) {
            textView.setText(R.string.last_week);
        } else {
            textView.setText(R.string.this_week);
        }
        return convertView;
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
        return R.layout.item_work_plan;
    }
}
