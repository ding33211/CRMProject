package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;

import java.util.List;

/**
 * Created by dingsigang on 16-8-23.
 */
public class ClueSpecIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

    private List<FollowTest> mList;
    private ClueSpecViewPagerRvAdapter mAdapter0;
    private ClueSpecViewPagerRvAdapter mAdapter1;


    public ClueSpecIndicatorViewPagerAdapter(List<FollowTest> mList) {
        this.mList = mList;
        mAdapter1 = new ClueSpecViewPagerRvAdapter(mList, ClueSpecViewPagerRvAdapter.POS_RECORD);
        mAdapter0 = new ClueSpecViewPagerRvAdapter(mList, ClueSpecViewPagerRvAdapter.POS_PLAN);

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.text_home_indicator, container, false);
        }
        TextView textView = (TextView) convertView;
        if (position == 0) {
            textView.setText(R.string.follow_plan);
        } else {
            textView.setText(R.string.follow_record);
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        Context context = container.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getViewPagerLayoutRes(), container, false);
        }
        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.rv_root_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(position == 0){
            recyclerView.setAdapter(mAdapter0);
        } else {
            recyclerView.setAdapter(mAdapter1);
        }
        return convertView;
    }

    @Override
    public int getViewPagerLayoutRes() {
        return R.layout.recyclerview_base;
    }
}
