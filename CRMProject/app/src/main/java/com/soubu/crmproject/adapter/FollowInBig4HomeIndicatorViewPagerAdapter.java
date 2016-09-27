package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;

import java.util.List;

/**
 * Created by dingsigang on 16-8-23.
 */
public class FollowInBig4HomeIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

    private FollowInBig4HomeViewPagerRvAdapter mAdapter0;
    private FollowInBig4HomeViewPagerRvAdapter mAdapter1;

    private View mEmptyView0;
    private View mEmptyView1;


    public FollowInBig4HomeIndicatorViewPagerAdapter(int which) {
        mAdapter0 = new FollowInBig4HomeViewPagerRvAdapter(FollowInBig4HomeViewPagerRvAdapter.POS_RECORD, Contants.IN_4_HOME, which);
        mAdapter1 = new FollowInBig4HomeViewPagerRvAdapter(FollowInBig4HomeViewPagerRvAdapter.POS_PLAN, Contants.IN_4_HOME, which);

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
        TextView textView = (TextView) convertView.findViewById(R.id.tv_tab);
        if (position == 0) {
            textView.setText(R.string.follow_record);
        } else {
            textView.setText(R.string.follow_plan);
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
            mEmptyView0 = convertView.findViewById(R.id.ll_empty);
        } else {
            recyclerView.setAdapter(mAdapter1);
            mEmptyView1 = convertView.findViewById(R.id.ll_empty);
        }
        return convertView;
    }

    @Override
    public int getViewPagerLayoutRes() {
        return R.layout.recyclerview_base;
    }

    public void setViewPagerData(int pos, List<FollowParams> list){
        if (pos == 0){
            Log.e("xxxxxxxxxx", list.isEmpty() + "");
            mAdapter0.setData(list);
            if(list.isEmpty()){
                mEmptyView0.setVisibility(View.VISIBLE);
            } else {
                mEmptyView0.setVisibility(View.GONE);
            }
        } else {
            mAdapter1.setData(list);
            if(list.isEmpty()){
                mEmptyView1.setVisibility(View.VISIBLE);
            } else {
                mEmptyView1.setVisibility(View.GONE);
            }
        }
    }
}
