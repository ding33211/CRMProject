package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.greendao.Staff;
import com.soubu.crmproject.model.EmployeeParams;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.utils.CharacterParser;
import com.soubu.crmproject.utils.PinyinComparator;
import com.soubu.crmproject.widget.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by dingsigang on 16-8-25.
 */
public class ChooseEmployeeRvAdapter extends RecyclerView.Adapter implements RecyclerViewFastScroller.BubbleTextGetter {
    private final int TYPE_TOP = 0x00;
    private final int TYPE_MID = 0x01;
    private final int TYPE_BOT = 0x02;
    private final int TYPE_ONLY = 0x03;
    private List<Staff> mParams;

    OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onClick(Staff staff);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ChooseEmployeeRvAdapter(Context context) {
        mParams = new ArrayList<>();
        DBHelper helper = DBHelper.getInstance(context);
        mParams.addAll(helper.getStaffDao().loadAll());
        Collections.sort(mParams, new PinyinComparator());
    }

    @Override
    public int getItemViewType(int position) {
        String thisLetter = mParams.get(position).getLetter();
        String lastLetter = null;
        String nextLetter = null;
        if (position != 0) {
            lastLetter = mParams.get(position - 1).getLetter();
        }
        if (position != getItemCount() - 1) {
            nextLetter = mParams.get(position + 1).getLetter();
        }
        if (TextUtils.equals(thisLetter, lastLetter)) {
            if (TextUtils.equals(thisLetter, nextLetter)) {
                return TYPE_MID;
            } else {
                return TYPE_BOT;
            }
        } else {
            if (TextUtils.equals(thisLetter, nextLetter)) {
                return TYPE_TOP;
            } else {
                return TYPE_ONLY;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_employee_recyclerview, parent, false);
        View vLine = view.findViewById(R.id.v_bottom_line);
        View vLetter = view.findViewById(R.id.ll_letter);
        switch (viewType) {
            case TYPE_ONLY:
                vLine.setVisibility(View.GONE);
                break;
            case TYPE_MID:
                vLetter.setVisibility(View.GONE);
                break;
            case TYPE_BOT:
                vLetter.setVisibility(View.GONE);
                vLine.setVisibility(View.GONE);
                break;
            case TYPE_TOP:
                break;
        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.tvName.setText(mParams.get(position).getNickname());
            holder1.tvDepartment.setText(mParams.get(position).getDepartment());
            holder1.tvPosition.setText(mParams.get(position).getPosition());
            holder1.tvLetter.setText(mParams.get(position).getLetter());
        }

    }


    @Override
    public String getTextToShowInBubble(final int pos) {
        return mParams.get(pos).getLetter();
    }

    @Override
    public int getItemCount() {
        return mParams.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        TextView tvDepartment;
        TextView tvPosition;
        TextView tvLetter;

        private ItemViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.tvDepartment = (TextView) itemView.findViewById(R.id.tv_department);
            this.tvPosition = (TextView) itemView.findViewById(R.id.tv_position);
            this.tvLetter = (TextView) itemView.findViewById(R.id.tv_letter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onClick(mParams.get(getLayoutPosition()));
            }
        }
    }


//    public void setData(List<Staff> list) {
//        if (mParams.size() > 0) {
//            mParams.clear();
//        }
//        mParams.addAll(list);
//        notifyDataSetChanged();
//    }


}
