package com.soubu.crmproject.adapter;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddSomethingRvAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_LABEL = 0x00;
    public static final int TYPE_OTHER = 0x01;
    public static final int TYPE_ITEM_REQUIRED_FILL = 0x02;  //必填
    public static final int TYPE_ITEM_REQUIRED_CHOOSE = 0x03;  // 必选
    public static final int TYPE_ITEM_CAN_FILL = 0x04;  //可填
    public static final int TYPE_ITEM_CAN_CHOOSE = 0x05;  //可选
    public static final int TYPE_ITEM_CAN_LOCATE = 0x06;  //可定位
    public static final int TYPE_ITEM_UNABLE = 0x07;  //不可点击

    //上一个点击的EditText
//    private EditText mEtLastClick;


    private List<AddItem> mList;
    private Activity mActivity;

    public AddSomethingRvAdapter(Activity activity) {
        mList = new ArrayList<>();
        mActivity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_something_recyclerview, parent, false);
        View vLabel = v.findViewById(R.id.rl_label);
        View vItem = v.findViewById(R.id.rl_item_content);
        TextView tvAction = (TextView) v.findViewById(R.id.tv_action);
        ImageView ivAction = (ImageView) v.findViewById(R.id.iv_action);
        View vOther = v.findViewById(R.id.ll_other_content);
        vLabel.setVisibility(View.GONE);
        vOther.setVisibility(View.GONE);
        switch (viewType) {
            case TYPE_LABEL:
                vLabel.setVisibility(View.VISIBLE);
                vItem.setVisibility(View.GONE);
                break;
            case TYPE_OTHER:
                vOther.setVisibility(View.VISIBLE);
                vItem.setVisibility(View.GONE);
                vOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case TYPE_ITEM_CAN_LOCATE:
                ivAction.setImageResource(R.drawable.locate);
            case TYPE_ITEM_CAN_CHOOSE:
                tvAction.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_REQUIRED_CHOOSE:
                tvAction.setText(R.string.required_choose);
                break;
            case TYPE_ITEM_REQUIRED_FILL:
                ivAction.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_CAN_FILL:
                tvAction.setVisibility(View.GONE);
                ivAction.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_UNABLE:
                //TODO
                break;
        }
        v.setTag(viewType);
//        vItem.setOnClickListener(this);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("xxxxxxxxxx", "position    " + position + "     holder    " + holder);
        int viewType = getItemViewType(position);
        ItemViewHolder holder1 = (ItemViewHolder) holder;
        holder1.etContent.setInputType(mList.get(position).getEditTextType());
//        holder1.etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    Log.e("zzzzzzzzzzz" , "ddddddddd");
//                    onEditTextLostFocus(v);
//                }
//            }
//        });
//        holder1.vContent.setTag(viewType);
//        holder1.vContent.setOnClickListener(this);
        if (position == getItemCount() - 1 || getItemViewType(position + 1) == TYPE_LABEL
                || getItemViewType(position + 1) == TYPE_OTHER) {
            holder1.vItemBottomLine.setVisibility(View.GONE);
        }
        switch (viewType) {
            case TYPE_LABEL:
                holder1.tvLabel.setText(mList.get(position).getTitleRes());
                break;
            case TYPE_OTHER:
                holder1.tvOther.setText(mList.get(position).getTitleRes());
                break;
            default:
                holder1.tvTitle.setText(mList.get(position).getTitleRes());
                break;
        }

    }

    @Override
    public void onClick(View v) {
        Log.e("zzzzzzzzzzz" , "hhhhhhhhhhhhh");
        int viewType = (int) v.getTag();
        View tvAction = v.findViewById(R.id.tv_action);
        View ivAction = v.findViewById(R.id.iv_action);
        EditText etContent = (EditText) v.findViewById(R.id.et_content);
        switch (viewType) {
            case TYPE_ITEM_REQUIRED_FILL:
                tvAction.setVisibility(View.GONE);
            case TYPE_ITEM_CAN_FILL:
                etContent.setVisibility(View.VISIBLE);
                WindowUtil.showSoftInput(v.getContext(), etContent);
                etContent.setTag(v);
                break;
        }

    }


    private void onEditTextLostFocus(View editText){
        if (editText != null && editText instanceof EditText) {
            String temp = ((EditText)editText).getText().toString();
            editText.setVisibility(View.GONE);
            View item = (View) editText.getTag();
            int viewType = (int) item.getTag();
            TextView tvAction = (TextView) item.findViewById(R.id.tv_action);
            if (!TextUtils.isEmpty(temp)) {
                tvAction.setText(temp);
                tvAction.setVisibility(View.VISIBLE);
            } else if (viewType == TYPE_ITEM_REQUIRED_FILL) {
                tvAction.setVisibility(View.VISIBLE);
            }
            WindowUtil.hideSoftInput(mActivity);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvLabel;
        TextView tvTitle;
        TextView tvOther;
        View vItemBottomLine;
        EditText etContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOther = (TextView) itemView.findViewById(R.id.tv_other_content);
            vItemBottomLine = itemView.findViewById(R.id.v_bottom_line);
            etContent = (EditText) itemView.findViewById(R.id.et_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("zzzzzzzzzzz" , "hhhhhhhhhhhhh");
                    int viewType = (int) v.getTag();
                    View tvAction = v.findViewById(R.id.tv_action);
                    View ivAction = v.findViewById(R.id.iv_action);
                    EditText etContent = (EditText) v.findViewById(R.id.et_content);
                    switch (viewType) {
                        case TYPE_ITEM_REQUIRED_FILL:
                            tvAction.setVisibility(View.GONE);
                        case TYPE_ITEM_CAN_FILL:
                            etContent.setVisibility(View.VISIBLE);
                            WindowUtil.showSoftInput(v.getContext(), etContent);
                            etContent.setTag(v);
                            break;
                    }
                }
            });
        }


    }

    public void setData(List<AddItem> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
