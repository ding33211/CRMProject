package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.widget.linebarchart.LineView;
import com.soubu.crmproject.widget.linebarchart.YAxisView;

import java.util.ArrayList;

/**
 * Created by dingsigang on 16-10-9.
 */
public class BasePerformanceActivityDelegate extends AppDelegate {
    LineView mLineView;
    YAxisView mLeftAxisView;
    YAxisView mRightAxisView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_base_performance;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mLineView = get(R.id.line_view);
        mLeftAxisView = get(R.id.v_left_line);
        mRightAxisView = get(R.id.v_right_line);
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            test.add(i + 1 + "月");
        }
        mLineView.setBottomTextList(test);
        mLineView.setDrawDotLine(false);
    }

    public void setLineDataList(ArrayList<ArrayList<Integer>> list, int space){
        mLineView.setDataList(list, mRightAxisView, space);
    }

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, int space, ArrayList<Integer> colorList){
        mLineView.setBarDataList(list, mLeftAxisView, space, colorList);
    }
}
