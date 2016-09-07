package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.greendao.Remind;
import com.soubu.crmproject.base.greendao.RemindDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 待办界面消息提醒adapter
 * Created by dingsigang on 16-8-15.
 */
public class MessageReminderRvAdapter extends RecyclerView.Adapter {

    List<Remind> mReminds;
    Calendar mCalendar;
    RemindDao mRemindDao;

    private static final int TYPE_TOP = 0x00; //上方展示时间,不展示上线
    private static final int TYPE_MIDDLE = 0x01; //中间不展示时间,展示上下线
    private static final int TYPE_BOTTOM = 0x02; //下方不展示时间,不展示下线
    private static final int TYPE_ONLY_ONE = 0x03; //下方不展示时间,不展示下线


    public MessageReminderRvAdapter(Context context, Date fisrtDayOfMouth) {
        DBHelper helper = DBHelper.getInstance(context);
        mRemindDao = helper.getRemindDao();
        mCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        mCalendar.setTime(fisrtDayOfMouth);
        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), 1, 0, 0, 0);
        Date startDate = mCalendar.getTime();
        mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date endDate = mCalendar.getTime();
        mReminds = mRemindDao.queryBuilder().where(RemindDao.Properties.Date.between(startDate, endDate)).list();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_todo_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holder1 = (ItemViewHolder)holder;
        switch (getItemViewType(position)){
            case TYPE_BOTTOM:
                holder1.downLine.setVisibility(View.INVISIBLE);
                break;
            case TYPE_MIDDLE:
                break;
            case TYPE_ONLY_ONE:
                holder1.downLine.setVisibility(View.INVISIBLE);
                holder1.upLine.setVisibility(View.INVISIBLE);
                break;
            case TYPE_TOP:
                holder1.upLine.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mReminds.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mReminds.size() == 1) {
            return TYPE_ONLY_ONE;
        }
        int lastDay = 0;
        int nextDay = 0;
        if (position != 0) {
            mCalendar.setTime(mReminds.get(position - 1).getDate());
            lastDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        mCalendar.setTime(mReminds.get(position).getDate());
        int thisDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        if(position != mReminds.size()){
            mCalendar.setTime(mReminds.get(position + 1).getDate());
            nextDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if(lastDay == thisDay){
            if(thisDay != nextDay){
                return TYPE_BOTTOM;
            } else {
                return TYPE_MIDDLE;
            }
        } else if(thisDay != lastDay){
            if(thisDay != nextDay){
                return TYPE_ONLY_ONE;
            } else {
                return TYPE_TOP;
            }
        }
        return TYPE_ONLY_ONE;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        View upLine;
        View downLine;
        ImageView ivCompleteState;
        ImageView ivRemindState;
        ImageView ivAction;
        TextView tvTime;
        TextView tvContent;
        TextView tvSubContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            upLine = itemView.findViewById(R.id.v_up_line);
            downLine = itemView.findViewById(R.id.v_down_line);
            ivCompleteState = (ImageView)itemView.findViewById(R.id.iv_complete_state);
            ivRemindState = (ImageView)itemView.findViewById(R.id.iv_remind_state);
            ivAction = (ImageView)itemView.findViewById(R.id.iv_remind_action);
            tvTime = (TextView)itemView.findViewById(R.id.tv_time);
            tvContent = (TextView)itemView.findViewById(R.id.tv_title);
            tvSubContent = (TextView)itemView.findViewById(R.id.tv_content);
        }
    }
}
