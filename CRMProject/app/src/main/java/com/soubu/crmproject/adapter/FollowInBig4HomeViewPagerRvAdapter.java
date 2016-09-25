package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-23.
 */
public class FollowInBig4HomeViewPagerRvAdapter extends RecyclerView.Adapter {
    List<FollowParams> mList;
    private static final int TYPE_TOP = 0X00;
    private static final int TYPE_MID = 0X01;
    private static final int TYPE_BOTTOM = 0X02;
    private static final int TYPE_ONLY = 0X03;

    public static final int POS_PLAN = 0X00;
    public static final int POS_RECORD = 0X01;


    private int mPos;
    private int mWhere;
    private int mWhich;
    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private CharSequence[] mFollowMethod;
    private CharSequence[] mFollowMethodWeb;


    public FollowInBig4HomeViewPagerRvAdapter(int pos, int where, int which) {
        mList = new ArrayList<>();
        mPos = pos;
        mCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        mCalendar.setTime(new Date());
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mWhere = where;
        mWhich = which;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mFollowMethod = parent.getContext().getResources().getStringArray(R.array.follow_method);
        mFollowMethodWeb = parent.getContext().getResources().getStringArray(R.array.follow_method_web);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clue_spec_viewpager_recyclerview, parent, false);
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
        if (mWhere == Contants.IN_4_HOME) {
            v.findViewById(R.id.ll_related_one).setVisibility(View.GONE);
        }
        if (mPos == POS_PLAN) {
            tvFollowState.setText(R.string.follow_function_with_colon);
        }
        switch (mWhich) {
            case Contants.FROM_CLUE:
                v.findViewById(R.id.ll_related_two).setVisibility(View.GONE);
                break;
            case Contants.FROM_CUSTOMER:
                v.findViewById(R.id.ll_follow_state).setVisibility(View.GONE);
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holder1 = null;
        if (holder instanceof ItemViewHolder) {
            holder1 = (ItemViewHolder) holder;
            holder1.tvTitle.setText(mList.get(position).getTitle());
            holder1.tvContent.setText(mList.get(position).getContent());
            holder1.tvFollowMethod.setText(mFollowMethod[SearchUtil.searchInArray(mFollowMethodWeb, mList.get(position).getMethod())]);
            CharSequence[] arrays = null;
            CharSequence[] arrayWebs = null;
            String entityType = mList.get(position).getEntityType();
            Context context = holder1.tvTitle.getContext();
            if (TextUtils.equals(entityType, Contants.FOLLOW_TYPE_OPPORTUNITY)) {
                arrays = SearchUtil.searchClueStateArray(context);
                arrayWebs = SearchUtil.searchClueStateWebArray(context);
            } else if (TextUtils.equals(entityType, Contants.FOLLOW_TYPE_DEAL)) {
                arrays = SearchUtil.searchBusinessOpportunityStateArray(context);
                arrayWebs = SearchUtil.searchBusinessOpportunityStateWebArray(context);
            } else if (TextUtils.equals(entityType, Contants.FOLLOW_TYPE_CONTRACT)) {
                arrays = SearchUtil.searchContractStateArray(context);
                arrayWebs = SearchUtil.searchContractStateWebArray(context);
            }
            if (!TextUtils.equals(entityType, Contants.FOLLOW_TYPE_OPPORTUNITY)) {
                holder1.vContact.setVisibility(View.VISIBLE);
                if (mList.get(position).getContact() != null) {
                    holder1.tvContact.setText(mList.get(position).getContact().getName());
                } else {
                    holder1.vContact.setVisibility(View.GONE);
                }
            }
            if (!TextUtils.equals(entityType, Contants.FOLLOW_TYPE_CUSTOMER)) {
                if (position == getItemCount() - 1) {
                    holder1.vLast.setVisibility(View.GONE);
                    if (arrays != null && arrayWebs != null) {
                        holder1.tvNowState.setText(arrays[SearchUtil.searchInArray(arrayWebs, mList.get(position).getStatus())]);
                    }
                } else {
                    if (TextUtils.equals(mList.get(position).getStatus(), mList.get(position + 1).getStatus())) {
                        holder1.vLast.setVisibility(View.GONE);
                        if (arrays != null && arrayWebs != null) {
                            holder1.tvNowState.setText(arrays[SearchUtil.searchInArray(arrayWebs, mList.get(position).getStatus())]);
                        }
                    } else {
                        holder1.vLast.setVisibility(View.VISIBLE);
                        if (arrays != null && arrayWebs != null) {
                            holder1.tvNowState.setText(arrays[SearchUtil.searchInArray(arrayWebs, mList.get(position).getStatus())]);
                            holder1.tvLastState.setText(arrays[SearchUtil.searchInArray(arrayWebs, mList.get(position + 1).getStatus())]);
                        }
                    }
                }
            }
            holder1.tvDate.setText(ConvertUtil.dateToYYYY_MM_DD_EEEE(mList.get(position).getFollowupAt()));
            holder1.tvTime.setText(ConvertUtil.dateToHH_mm(mList.get(position).getFollowupAt()));
        }
        if (mPos == POS_PLAN) {
//            mCalendar.setTime(new Date(mList.get(position).getTime()));
//            boolean isToday = false;
//            if(mCalendar.get(Calendar.DAY_OF_MONTH) == mDay)
//            {
//                if(mCalendar.get(Calendar.MONTH) == mMonth && mCalendar.get(Calendar.DAY_OF_MONTH) == mDay){
//                    isToday = true;
//                }
//            }
//            if(isToday){
//                if(mList.get(position).getFollowState() == FollowTest.STATE_COMPLETE){
//                    holder1.ivFollowState.setImageResource(R.drawable.today_complete);
//                } else {
//                    holder1.ivFollowState.setImageResource(R.drawable.today_not_complete);
//                }
//            } else {
//                if(mList.get(position).getFollowState() == FollowTest.STATE_COMPLETE){
//                    holder1.ivFollowState.setImageResource(R.drawable.other_day_complete);
//                } else {
//                    holder1.ivFollowState.setImageResource(R.drawable.other_day_not_complete);
//                }
//            }
//            holder1.ivAlarm.setVisibility(View.GONE);
//            if(mList.get(position).isNeedRecord()){
//                holder1.ivAlarm.setVisibility(View.VISIBLE);
//            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        mCalendar.setTime(mList.get(position).getFollowupAt());
        int thisDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int lastDay = -1;
        int nextDay = -1;
        if (position != 0) {
            mCalendar.setTime(mList.get(position - 1).getFollowupAt());
            lastDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if (position != getItemCount() - 1) {
            mCalendar.setTime(mList.get(position + 1).getFollowupAt());
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
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
        TextView tvDate;
        TextView tvContact;
        TextView tvFollowMethod;
        View vLast;
        View vContact;
        TextView tvLastState;
        TextView tvNowState;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivFollowState = (ImageView) itemView.findViewById(R.id.iv_complete_state);
            ivAlarm = (ImageView) itemView.findViewById(R.id.iv_alarm);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvContact = (TextView) itemView.findViewById(R.id.tv_related_two_value);
            vLast = itemView.findViewById(R.id.ll_last_state);
            tvLastState = (TextView) itemView.findViewById(R.id.tv_follow_state_before);
            tvNowState = (TextView) itemView.findViewById(R.id.tv_follow_state_next);
            vContact = itemView.findViewById(R.id.ll_related_two);
            tvFollowMethod = (TextView) itemView.findViewById(R.id.tv_follow_method);
        }
    }

    public void setData(List<FollowParams> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
