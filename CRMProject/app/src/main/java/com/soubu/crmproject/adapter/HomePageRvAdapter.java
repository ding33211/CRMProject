package com.soubu.crmproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

/**
 * 首页的RecyclerView adapter
 * Created by dingsigang on 16-8-10.
 */
public class HomePageRvAdapter extends RecyclerView.Adapter {

    private static final int TYPE_PERFORMANCE_GOAL = 0x00;//业绩目标
    private static final int TYPE_WORK_PLAN = 0x01;//工作计划
    private static final int TYPE_SALES_PERFORMANCE = 0x02;//销售业绩
    private static final int TYPE_SALES_EXECUTIVE = 0x03;//销售执行力

    @Override
    public int getItemCount() {
        return 4;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_PERFORMANCE_GOAL;
            case 1:
                return TYPE_WORK_PLAN;
            case 2:
                return TYPE_SALES_PERFORMANCE;
            case 3:
                return TYPE_SALES_EXECUTIVE;
            default:
                return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if(viewType == TYPE_WORK_PLAN){
            View v = LayoutInflater.from(context).inflate(R.layout.item_work_plan, parent, false);
            return new ItemViewHolder(v);
        }
        View v = LayoutInflater.from(context).inflate(R.layout.item_home_recyclerview, parent, false);
        View viewPagerItem;
        IndicatorViewPager.IndicatorViewPagerAdapter adapter = null;
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.vp_content);
        Indicator indicator = (Indicator) v.findViewById(R.id.fiv_indicator);
        indicator.setScrollBar(new ColorBar(context, context.getResources().getColor(R.color.colorPrimary), 5));
        float unSelectSize = 16;
        //字体都用一个大小
        float selectSize = unSelectSize;
        int selectColor = context.getResources().getColor(R.color.colorPrimary);
        int unSelectColor = context.getResources().getColor(R.color.subtitle_grey);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);

        switch (viewType) {
            case TYPE_PERFORMANCE_GOAL:
                adapter = new PerformanceGoalAdapter(context);
                break;
//            case TYPE_WORK_PLAN:
//                adapter = new WorkPlanAdapter(context);
//                break;
            case TYPE_SALES_PERFORMANCE:
                adapter = new SalesPerformanceAdapter(context);
                break;
            case TYPE_SALES_EXECUTIVE:
                adapter = new SalesExecutiveAdapter(context);
                break;
            default:
                break;
        }
        viewPagerItem = LayoutInflater.from(context).inflate(adapter.getViewPagerLayoutRes(), parent, false);
        indicatorViewPager.setAdapter(adapter);
        if (viewPagerItem != null) {
            viewPagerItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, viewPagerItem.getMeasuredHeight());
            viewPager.setLayoutParams(lp);
        }
        indicatorViewPager.setCurrentItem(1, false);
//        if (viewType == TYPE_PERFORMANCE_GOAL) {
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_news_list, parent, false);
//            ItemViewHolder vh = new ItemViewHolder(v);
//            return vh;
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.list_footer, null);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            return new FooterViewHolder(view);
//        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            switch (position) {
                case 0:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.performance_goal);
                    break;
//                case 1:
//                    ((ItemViewHolder) holder).tvTitle.setText(R.string.work_plan);
//                    break;
                case 2:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.sales_performance);
                    break;
                case 3:
                    ((ItemViewHolder) holder).tvTitle.setText(R.string.sales_executive);
                    break;
                default:
                    break;
            }
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

}
