package com.soubu.crmproject.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.FollowTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-23.
 */
public class ClueSpecRvAdapter extends RecyclerView.Adapter {
    List<FollowTest> mList;
    private static final int TYPE_TOP = 0X00;
    private static final int TYPE_MID = 0X01;
    private static final int TYPE_BOTTOM = 0X02;
    private static final int TYPE_ONLY = 0X03;

    public static final int POS_PLAN = 0X00;
    public static final int POS_RECORD = 0X01;


    private int mPos;
    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;


    public ClueSpecRvAdapter(List<FollowTest> list, int pos) {
        mList = list;
        mPos = pos;
        mCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        mCalendar.setTime(new Date());
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clue_spec_recyclerview, parent, false);
        View llDate = v.findViewById(R.id.ll_date);
        View bottomLine = v.findViewById(R.id.v_bottom_line);
        TextView tvFollowState = (TextView) v.findViewById(R.id.tv_follow_state_label);
        switch (viewType) {
            case TYPE_ONLY:
                bottomLine.setVisibility(View.GONE);
                break;
            case TYPE_MID:
                llDate.setVisibility(View.GONE);
                break;
            case TYPE_BOTTOM:
                llDate.setVisibility(View.GONE);
                bottomLine.setVisibility(View.GONE);
                break;
            case TYPE_TOP:
                break;
        }
        if(mPos == POS_PLAN){
             tvFollowState.setText(R.string.follow_function_with_colon);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holder1 = null;
        if(holder instanceof ItemViewHolder){
            holder1 = (ItemViewHolder) holder;
        } else {
            return;
        }
        if(mPos == POS_PLAN){
            mCalendar.setTime(new Date(mList.get(position).getTime()));
            boolean isToday = false;
            if(mCalendar.get(Calendar.DAY_OF_MONTH) == mDay)
            {
                if(mCalendar.get(Calendar.MONTH) == mMonth && mCalendar.get(Calendar.DAY_OF_MONTH) == mDay){
                    isToday = true;
                }
            }
            if(isToday){
                if(mList.get(position).getFollowState() == FollowTest.STATE_COMPLETE){
                    holder1.ivFollowState.setImageResource(R.drawable.today_complete);
                } else {
                    holder1.ivFollowState.setImageResource(R.drawable.today_not_complete);
                }
            } else {
                if(mList.get(position).getFollowState() == FollowTest.STATE_COMPLETE){
                    holder1.ivFollowState.setImageResource(R.drawable.other_day_complete);
                } else {
                    holder1.ivFollowState.setImageResource(R.drawable.other_day_not_complete);
                }
            }
            holder1.ivAlarm.setVisibility(View.GONE);
            if(mList.get(position).isNeedRecord()){
                holder1.ivAlarm.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        mCalendar.setTime(new Date(mList.get(position).getTime()));
        int thisDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int lastDay = -1;
        int nextDay = -1;
        if (position != 0) {
            mCalendar.setTime(new Date(mList.get(position - 1).getTime()));
            lastDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if (position != getItemCount() - 1) {
            mCalendar.setTime(new Date(mList.get(position + 1).getTime()));
            nextDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if (thisDay == lastDay) {
            if (thisDay == nextDay) {
                return TYPE_MID;
            } else {
                return TYPE_BOTTOM;
            }
        } else {
            if (thisDay == nextDay) {
                return TYPE_TOP;
            } else {
                return TYPE_ONLY;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFollowState;
        ImageView ivAlarm;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivFollowState = (ImageView) itemView.findViewById(R.id.iv_complete_state);
            ivAlarm = (ImageView) itemView.findViewById(R.id.iv_alarm);
        }
    }
}
