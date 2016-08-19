package com.soubu.crmproject.delegate;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.TodoEventsAdapter;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.greendao.Remind;
import com.soubu.crmproject.base.greendao.RemindDao;
import com.soubu.crmproject.widget.customcalendar.CompactCalendarView;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-9.
 */
public class TodoFragmentDelegate extends BaseFragmentDelegate {
    CompactCalendarView mCcvCalendar;
    TodoEventsAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_todo;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        final Context context = getActivity();
        mCcvCalendar = get(R.id.ccv_calendar);
        mCcvCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mAdapter = new TodoEventsAdapter(context, firstDayOfNewMonth);
                mAdapter.notifyDataSetChanged();
            }
        });
        ((TextView)get(R.id.tv_title)).setText(R.string.remind_message);
        mAdapter = new TodoEventsAdapter(context, new Date());
        final ViewPager viewPager = get(R.id.vp_content);
        Indicator indicator = get(R.id.fiv_indicator);
        indicator.setScrollBar(new ColorBar(context, Color.RED, 5));
        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;
        int selectColor = context.getResources().getColor(R.color.bottom_bar_text_selected_color);
        int unSelectColor = context.getResources().getColor(R.color.bottom_bar_text_normal_color);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(mAdapter);

    }
}
