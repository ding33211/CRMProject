package com.soubu.crmproject.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.utils.WindowUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddSomethingRvAdapter extends RecyclerView.Adapter {

    public static final int TYPE_LABEL = 0x00;
    public static final int TYPE_OTHER = 0x01;
    public static final int TYPE_ITEM_REQUIRED_FILL = 0x02;  //必填
    public static final int TYPE_ITEM_REQUIRED_CHOOSE = 0x03;  // 必选
    public static final int TYPE_ITEM_CAN_FILL = 0x04;  //可填
    public static final int TYPE_ITEM_CAN_CHOOSE = 0x05;  //可选
    public static final int TYPE_ITEM_CAN_LOCATE = 0x06;  //可定位
    public static final int TYPE_ITEM_UNABLE = 0x07;  //不可点击
    public static final int TYPE_ITEM_REQUIRED_CHOOSE_DATE = 0x08;  //必选日期
    public static final int TYPE_ITEM_CAN_CHOOSE_DATE = 0x09;  //可选日期
    public static final int TYPE_NOT_PASS = 0x10;  //审核状态


    private List<AddItem> mList;
    private Activity mActivity;

    private List<Integer> mRequiredPosList;

    //防止出现编辑框没有转化成数据,就点击了保存的情况
    private View mVStillFocus;

    public AddSomethingRvAdapter(Activity activity) {
        mList = new ArrayList<>();
        mActivity = activity;
        mRequiredPosList = new ArrayList<>();
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
            case TYPE_ITEM_CAN_CHOOSE_DATE:
                tvAction.setVisibility(View.INVISIBLE);
                break;
            case TYPE_ITEM_REQUIRED_CHOOSE:
            case TYPE_ITEM_REQUIRED_CHOOSE_DATE:
                tvAction.setText(R.string.required_choose);
                break;
            case TYPE_ITEM_REQUIRED_FILL:
                ivAction.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_CAN_FILL:
                tvAction.setVisibility(View.INVISIBLE);
                ivAction.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_UNABLE:
                //TODO
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(holder.getLayoutPosition());
        if (viewType == TYPE_ITEM_REQUIRED_CHOOSE || viewType == TYPE_ITEM_REQUIRED_FILL || viewType == TYPE_ITEM_CAN_LOCATE) {
            if (!mRequiredPosList.contains(position)) {
                mRequiredPosList.add(position);
            }
        }
        ItemViewHolder holder1 = (ItemViewHolder) holder;
        holder1.etContent.setInputType(mList.get(holder.getLayoutPosition()).getEditTextType() | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        holder1.etContent.setText(mList.get(position).getContent());
        holder1.etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mVStillFocus = v;
                }
                if (!hasFocus) {
                    mVStillFocus = null;
                    onEditTextLostFocus(v, position);
                }
            }
        });
        holder1.vItemBottomLine.setVisibility(View.VISIBLE);
        if (holder.getLayoutPosition() == getItemCount() - 1 || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_LABEL
                || getItemViewType(holder.getLayoutPosition() + 1) == TYPE_OTHER) {
            holder1.vItemBottomLine.setVisibility(View.INVISIBLE);
        }
        String text = mList.get(position).getContent();
        holder1.tvAction.setText("");
        holder1.tvAction.setVisibility(View.INVISIBLE);
        if (TextUtils.isEmpty(text)) {
            if (viewType == TYPE_ITEM_REQUIRED_FILL || viewType == TYPE_ITEM_REQUIRED_CHOOSE) {
                holder1.tvAction.setText(viewType == TYPE_ITEM_REQUIRED_FILL ? R.string.required_fill : R.string.required_choose);
                holder1.tvAction.setVisibility(View.VISIBLE);
            }
            if ((viewType == TYPE_ITEM_CAN_CHOOSE_DATE || viewType == TYPE_ITEM_REQUIRED_CHOOSE_DATE)
                    && mList.get(position).getDate() != null) {
                holder1.tvAction.setText(ConvertUtil.dateToYYYY_MM_DD(mList.get(position).getDate()));
                holder1.tvAction.setVisibility(View.VISIBLE);
            }
        } else {
            if(viewType == TYPE_ITEM_REQUIRED_CHOOSE || viewType == TYPE_ITEM_CAN_CHOOSE){
                int arrayRes = mList.get(position).getArrayRes();
                int webArrayRes = mList.get(position).getWebArrayRes();
                if(arrayRes != 0 && webArrayRes != 0){
                    text = mActivity.getResources().getStringArray(arrayRes)[SearchUtil.searchInArray(mActivity.getResources().getStringArray(webArrayRes), text)];
                }
            }
            holder1.tvAction.setText(text);
            holder1.tvAction.setVisibility(View.VISIBLE);
        }
        switch (viewType) {
            case TYPE_LABEL:
                holder1.tvLabel.setText(mList.get(holder.getLayoutPosition()).getTitleRes());
                break;
            case TYPE_OTHER:
                holder1.tvOther.setText(mList.get(holder.getLayoutPosition()).getTitleRes());
                break;
            default:
                holder1.tvTitle.setText(mList.get(holder.getLayoutPosition()).getTitleRes());
                break;
        }

    }

    private void onEditTextLostFocus(View editText, int pos) {
        if (editText != null && editText instanceof EditText) {
            String temp = ((EditText) editText).getText().toString();
            editText.setVisibility(View.GONE);
            mList.get(pos).setContent(((EditText) editText).getText().toString());
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

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvLabel;
        public TextView tvTitle;
        public TextView tvOther;
        public View vItemBottomLine;
        public EditText etContent;
        public TextView tvAction;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOther = (TextView) itemView.findViewById(R.id.tv_other_content);
            vItemBottomLine = itemView.findViewById(R.id.v_bottom_line);
            etContent = (EditText) itemView.findViewById(R.id.et_content);
            tvAction = (TextView) itemView.findViewById(R.id.tv_action);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("22222222222", "getAdapterPosition    :   " + getAdapterPosition() + "  getLayoutPosition   " + getLayoutPosition());
            int viewType = getItemViewType();
            View ivAction = v.findViewById(R.id.iv_action);
            switch (viewType) {
                case TYPE_ITEM_REQUIRED_FILL:
                case TYPE_ITEM_CAN_FILL:
                case TYPE_ITEM_CAN_LOCATE:
                    tvAction.setVisibility(View.INVISIBLE);
                    etContent.setVisibility(View.VISIBLE);
                    WindowUtil.showSoftInput(v.getContext(), etContent);
                    etContent.setTag(getAdapterPosition());
                    etContent.setSelection(etContent.getText().length());
                    Log.e("xxxxxxxxxx", "inputtype    :   " + etContent.getInputType());
                    v.setTag(viewType);
                    etContent.setTag(v);
                    break;
                case TYPE_ITEM_CAN_CHOOSE_DATE:
                case TYPE_ITEM_REQUIRED_CHOOSE_DATE:
                    Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                    c.setTime(mList.get(getAdapterPosition()).getDate());
                    new DatePickerDialog(mActivity,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                                    c.set(year, monthOfYear, dayOfMonth);
                                    mList.get(getAdapterPosition()).setDate(c.getTime());
                                    notifyDataSetChanged();
                                }
                            }
                            // 设置初始日期
                            , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case TYPE_ITEM_CAN_CHOOSE:
                case TYPE_ITEM_REQUIRED_CHOOSE:
                    if (mList.get(getAdapterPosition()).getTitleRes() == R.string.manager) {
                        mList.get(getAdapterPosition()).setContent("56f0f862ca3e870a6457156a");
                        notifyDataSetChanged();
                    } else {
                        final AddItem item = mList.get(getAdapterPosition());
                        if (item.getArrayRes() != 0 && item.getWebArrayRes() != 0) {
                            ShowWidgetUtil.showMultiItemDialog(mActivity, item.getTitleRes(), item.getArrayRes(), false, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CharSequence[] webArray = mActivity.getResources().getTextArray(item.getWebArrayRes());
                                    mList.get(getAdapterPosition()).setContent(webArray[which].toString());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }

    public void setData(List<AddItem> list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }
        mList.addAll(list);
    }

    public List<AddItem> getData() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 验证有没有填写必填和勾选
     *
     * @return true 通过  false 没通过
     */
    public boolean verifyRequired() {
        if (mVStillFocus != null) {
            mVStillFocus.clearFocus();
        }
        for (int pos : mRequiredPosList) {
            if (TextUtils.isEmpty(mList.get(pos).getContent())) {
                return false;
            }
        }
        return true;
    }

}
