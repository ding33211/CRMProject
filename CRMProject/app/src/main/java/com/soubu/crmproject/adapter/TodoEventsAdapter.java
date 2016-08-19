package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;

import java.util.Date;

/**
 * Created by dingsigang on 16-8-15.
 */
public class TodoEventsAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {


    Context context;
    MessageReminderRvAdapter mAdapter;

    public TodoEventsAdapter(Context context, Date fisrtDayOfMouth) {
        this.context = context;
        mAdapter = new MessageReminderRvAdapter(context, fisrtDayOfMouth);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.text_home_indicator, container, false);
        }
        TextView textView = (TextView) convertView;
        if (position == 0) {
            textView.setText(R.string.no_reminder);
        } else if (position == 1) {
            textView.setText(R.string.reminder);
        } else {
            textView.setText(R.string.all);
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recyclerview_base, container, false);
        }
        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.rv_root_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
        return convertView;
    }


}
