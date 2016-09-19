package com.soubu.crmproject.delegate;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.FollowInBig4HomeIndicatorViewPagerAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.view.activity.AddFollowActivity;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

import java.io.Serializable;
import java.util.List;

import retrofit2.http.GET;


/**
 * 大四样主页代理
 * Created by lakers on 16/8/21.
 */
public class Big4HomeActivityDelegate extends AppDelegate {
    ViewPager mViewPager;
    Indicator mIndicator;

    private int mFrom = 0;
    private Serializable mSerializable;
    private FollowInBig4HomeIndicatorViewPagerAdapter mIndicatorViewPagerAdapter;
    public static final int REQUEST_ADD_FOLLOW = 1003;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_clue_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        switch (mFrom){
            case Contants.FROM_CUSTOMER:
                get(R.id.rl_customer_content).setVisibility(View.VISIBLE);
//                get(R.id.ll_state_content).setVisibility(View.GONE);
//                get(R.id.rl_contact_method).setVisibility(View.GONE);
//                get(R.id.rl_contact).setVisibility(View.VISIBLE);
//                ((TextView)get(R.id.tv_subtitle_label)).setText(R.string.customer_property);
                ((TextView)get(R.id.tv_go_left)).setText(R.string.customer_spec);
                ((TextView)get(R.id.tv_go_right)).setText(R.string.contact);
                break;
            case Contants.FROM_CONTRACT:
//                get(R.id.rl_customer_content).setVisibility(View.VISIBLE);
                get(R.id.iv_contract_review_state).setVisibility(View.VISIBLE);
//                get(R.id.ll_contract_content).setVisibility(View.VISIBLE);
//                ((TextView)get(R.id.tv_left_label)).setText(R.string.sales_back_plan);
//                ((TextView)get(R.id.tv_right_label)).setText(R.string.already_sales_back);
//                ((TextView)get(R.id.tv_subtitle_label)).setText(R.string.related_customer);
                ((TextView)get(R.id.tv_go_left)).setText(R.string.contract_spec);
                ((TextView)get(R.id.tv_go_right)).setText(R.string.customer_home);
                break;
            case Contants.FROM_BUSINESS_OPPORTUNITY:
                ((TextView)get(R.id.tv_go_left)).setText(R.string.business_opportunity_spec);
                ((TextView)get(R.id.tv_go_right)).setText(R.string.customer_home);
                break;
            case Contants.FROM_CLUE:
//                get(R.id.ll_subtitle_label).setVisibility(View.GONE);
                get(R.id.ll_go_right).setVisibility(View.GONE);
                ((TextView)get(R.id.tv_go_left)).setText(R.string.clue_spec);
                break;
        }
        mIndicatorViewPagerAdapter = new FollowInBig4HomeIndicatorViewPagerAdapter();
        mViewPager = get(R.id.vp_content);
        mIndicator = get(R.id.fiv_indicator);
        mIndicator.setScrollBar(new ColorBar(getActivity(), getActivity().getResources().getColor(R.color.colorPrimary), 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize;
        int selectColor = getActivity().getResources().getColor(R.color.colorPrimary);
        int unSelectColor = getActivity().getResources().getColor(R.color.subtitle_grey);
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        indicatorViewPager.setAdapter(mIndicatorViewPagerAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        get(R.id.ll_fill_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, mFrom);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_RECORD);
                intent.putExtra(Contants.EXTRA_ENTITY, mSerializable);
                getActivity().startActivityForResult(intent, REQUEST_ADD_FOLLOW);
            }
        });
        get(R.id.ll_add_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, mFrom);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_PLAN);
                intent.putExtra(Contants.EXTRA_ENTITY, mSerializable);
                getActivity().startActivity(intent);
            }
        });
    }

    public void setEntity(Serializable entity){
        mSerializable = entity;
    }

    public void setFrom(int from){
        mFrom = from;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public void setViewPagerData(int pos, List<FollowParams> list){
        mIndicatorViewPagerAdapter.setViewPagerData(pos, list);
    }

}
