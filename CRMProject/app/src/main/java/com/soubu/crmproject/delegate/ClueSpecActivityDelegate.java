package com.soubu.crmproject.delegate;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;


/**
 * Created by lakers on 16/8/21.
 */
public class ClueSpecActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_clue_spec;
    }

    @Override
    public void initWidget() {
        super.initWidget();
//        View v = LayoutInflater.from(get).inflate(R.layout.item_home_recyclerview, parent, false);
//        View viewPagerItem = null;
//        IndicatorViewPager.IndicatorViewPagerAdapter adapter = null;
//        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.vp_content);
//        Indicator indicator = (Indicator) v.findViewById(R.id.fiv_indicator);
//        indicator.setScrollBar(new ColorBar(getActivity(), Color.RED, 5));
//        float unSelectSize = 16;
//        float selectSize = unSelectSize * 1.2f;
//        int selectColor = getActivity().getResources().getColor(R.color.bottom_bar_text_selected_color);
//        int unSelectColor = getActivity().getResources().getColor(R.color.bottom_bar_text_normal_color);
//        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
//        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
//        indicatorViewPager.setAdapter(new );
    }
}
