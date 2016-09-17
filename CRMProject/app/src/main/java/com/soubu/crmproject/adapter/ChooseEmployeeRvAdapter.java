package com.soubu.crmproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.EmployeeParams;
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



    private List<EmployeeParams> mParams;


    public ChooseEmployeeRvAdapter() {
        mParams = new ArrayList<>();
        EmployeeParams params = new EmployeeParams();
        params.setName("张老五");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("大大");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("给我个");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("呵呵");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("怕怕的");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("大卖场");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("去");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("怕买跑车");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("草庙村");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("且");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("业务员");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("去去去");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("长毛床");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("老婆出去");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("camouflage");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("大");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("切切");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("服务");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("服务你");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("张老五");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("磨齿机");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("流量卡");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("啊啊啊");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("大大大");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("哈哈哈");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("不不不");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("那你呢");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("抠门");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("以以i");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("哦哦哦");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("我问问");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("切切");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
        params = new EmployeeParams();
        params.setName("嘻嘻嘻");
        params.setDepartment("123");
        params.setPosition("456");
        mParams.add(params);
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
            holder1.tvName.setText(mParams.get(position).getName());
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

    class ItemViewHolder extends RecyclerView.ViewHolder {
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
        }
    }


    public void setData(List<EmployeeParams> list) {
        if (mParams.size() > 0) {
            mParams.clear();
        }
        mParams.addAll(list);
        notifyDataSetChanged();
    }


}
