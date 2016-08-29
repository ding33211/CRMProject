package com.soubu.crmproject.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueSpecIndicatorViewPagerAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

import java.util.List;


/**
 * 大四样主页代理
 * Created by lakers on 16/8/21.
 */
public class Big4HomeActivityDelegate extends AppDelegate {
    ViewPager mViewPager;
    Indicator mIndicator;

    public static final int FROM_CLUE = 0x00;
    public static final int FROM_CUSTOMER = 0x01;
    public static final int FROM_BUSINESS_OPPORTUNITY = 0x02;
    public static final int FROM_CONTRACT = 0x03;

    private int mFrom = 0;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_clue_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        switch (mFrom){
            case FROM_CUSTOMER:
                get(R.id.rl_customer_content).setVisibility(View.VISIBLE);
                get(R.id.ll_clue_content).setVisibility(View.GONE);
                break;
            case FROM_CONTRACT:

        }
        mViewPager = get(R.id.vp_content);
        mIndicator = get(R.id.fiv_indicator);
        mIndicator.setScrollBar(new ColorBar(getActivity(), getActivity().getResources().getColor(R.color.colorPrimary), 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;
        int selectColor = getActivity().getResources().getColor(R.color.colorPrimary);
        int unSelectColor = getActivity().getResources().getColor(R.color.subtitle_grey);
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
    }

    public void setIndicatorViewPagerAdapter(List<FollowTest> list){
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        indicatorViewPager.setAdapter(new ClueSpecIndicatorViewPagerAdapter(list));
        indicatorViewPager.setCurrentItem(1, false);
    }

    public void setFrom(int from){
        mFrom = from;
    }

}
