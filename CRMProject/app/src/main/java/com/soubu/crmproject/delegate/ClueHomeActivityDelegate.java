package com.soubu.crmproject.delegate;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueSpecIndicatorViewPagerAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

import java.util.List;


/**
 * Created by lakers on 16/8/21.
 */
public class ClueHomeActivityDelegate extends AppDelegate {
    ViewPager mViewPager;
    Indicator mIndicator;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_clue_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.clue_home);
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

}
