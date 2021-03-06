package com.soubu.crmproject.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.RegularUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.utils.WindowUtil;
import com.soubu.crmproject.view.activity.AddBusinessOpportunityActivity;
import com.soubu.crmproject.view.activity.ChooseEmployeeActivity;
import com.soubu.crmproject.view.activity.CustomerActivity;

import java.util.ArrayList;
import java.util.Calendar;
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
    public static final int TYPE_HEADER_VIEW = 0x11;



    public static final int REQUEST_CODE_CHOOSE_CUSTOMER = 1100;
    public static final int REQUEST_CODE_CHOOSE_MANAGER = 1101;


    private List<AddItem> mList;
    private Activity mActivity;

    private List<Integer> mRequiredPosList;
    private int mMobilePos = -1;
    private int mEmailPos = -1;
    private int mTelPos = -1;
    private int mRelatedCustomer = -1;

    //防止出现编辑框没有转化成数据,就点击了保存的情况
    private View mVStillFocus;

    private int mLastClickPosBeforeLeave = 0;

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
        View vItem = v.findViewById(R.id.fl_item_content);
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
                tvAction.setTextColor(mActivity.getResources().getColor(R.color.title_black));
                ivAction.setVisibility(View.GONE);
                v.setClickable(false);
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
        if (mList.get(position).getTitleRes() == R.string.mobile) {
            mMobilePos = position;
        }
        if (mList.get(position).getTitleRes() == R.string.email) {
            mEmailPos = position;
        }
        if (mList.get(position).getTitleRes() == R.string.phone) {
            mTelPos = position;
        }
        if(mList.get(position).getTitleRes() == R.string.related_customer){
            mRelatedCustomer = position;
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
        if (TextUtils.isEmpty(text) || TextUtils.equals(text, "0")) {
            if (viewType == TYPE_ITEM_REQUIRED_FILL || viewType == TYPE_ITEM_REQUIRED_CHOOSE || viewType == TYPE_ITEM_REQUIRED_CHOOSE_DATE) {
                holder1.tvAction.setText(viewType == TYPE_ITEM_REQUIRED_FILL ? R.string.required_fill : R.string.required_choose);
                holder1.tvAction.setTextColor(mActivity.getResources().getColor(R.color.line_color));
                holder1.tvAction.setVisibility(View.VISIBLE);
            }
            if ((viewType == TYPE_ITEM_CAN_CHOOSE_DATE || viewType == TYPE_ITEM_REQUIRED_CHOOSE_DATE)
                    && mList.get(position).getDate() != null) {
                holder1.tvAction.setText(ConvertUtil.dateToYYYY_MM_DD(mList.get(position).getDate()));
                holder1.tvAction.setTextColor(mActivity.getResources().getColor(R.color.filter_tab_text_color));
                holder1.tvAction.setVisibility(View.VISIBLE);
            }
        } else {
            if (viewType == TYPE_ITEM_REQUIRED_CHOOSE || viewType == TYPE_ITEM_CAN_CHOOSE) {
                int arrayRes = mList.get(position).getArrayRes();
                int webArrayRes = mList.get(position).getWebArrayRes();
                if (arrayRes != 0 && webArrayRes != 0) {
                    text = mActivity.getResources().getStringArray(arrayRes)[SearchUtil.searchInArray(mActivity.getResources().getStringArray(webArrayRes), text)];
                }
            }
            holder1.tvAction.setText(text);
            holder1.tvAction.setTextColor(mActivity.getResources().getColor(R.color.filter_tab_text_color));
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
            editText.setVisibility(View.INVISIBLE);
            mList.get(pos).setContent(temp);
            View item = (View) editText.getTag();
            int viewType = (int) item.getTag();
            TextView tvAction = (TextView) item.findViewById(R.id.tv_action);
            if (!TextUtils.isEmpty(temp)) {
                tvAction.setText(temp);
                tvAction.setTextColor(mActivity.getResources().getColor(R.color.filter_tab_text_color));
                tvAction.setVisibility(View.VISIBLE);
            } else if (viewType == TYPE_ITEM_REQUIRED_FILL) {
                tvAction.setText(viewType == TYPE_ITEM_REQUIRED_FILL ? R.string.required_fill : R.string.required_choose);
                tvAction.setTextColor(mActivity.getResources().getColor(R.color.line_color));
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
            int viewType = getItemViewType();
            switch (viewType) {
                case TYPE_ITEM_REQUIRED_FILL:
                case TYPE_ITEM_CAN_FILL:
                case TYPE_ITEM_CAN_LOCATE:
                    tvAction.setVisibility(View.INVISIBLE);
                    etContent.setVisibility(View.VISIBLE);
                    WindowUtil.showSoftInput(v.getContext(), etContent);
                    etContent.setTag(getLayoutPosition());
                    etContent.setSelection(etContent.getText().length());
                    v.setTag(viewType);
                    etContent.setTag(v);
                    break;
                case TYPE_ITEM_CAN_CHOOSE_DATE:
                case TYPE_ITEM_REQUIRED_CHOOSE_DATE:
                    final Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                    if (mList.get(getLayoutPosition()).getDate() != null) {
                        c.setTime(mList.get(getLayoutPosition()).getDate());
                    }
                    new DatePickerDialog(mActivity,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Calendar chooseC = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                                    chooseC.set(year, monthOfYear, dayOfMonth);
                                    if (mList.get(getLayoutPosition()).getTitleRes() == R.string.signed_date) {
                                        if (chooseC.getTimeInMillis() - c.getTimeInMillis() > 24 * 3600 * 1000) {
                                            ShowWidgetUtil.showShort(R.string.signed_date_choose_error_message);
                                            return;
                                        }
                                    }
                                    mList.get(getLayoutPosition()).setDate(chooseC.getTime());
                                    notifyDataSetChanged();
                                }
                            }
                            // 设置初始日期
                            , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case TYPE_ITEM_CAN_CHOOSE:
                case TYPE_ITEM_REQUIRED_CHOOSE:
                    if (mList.get(getLayoutPosition()).getTitleRes() == R.string.manager) {
                        Intent intent = new Intent(mActivity, ChooseEmployeeActivity.class);
                        intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_ADD_SOMETHING_ACTIVITY);
                        mActivity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_MANAGER);
                        mLastClickPosBeforeLeave = getLayoutPosition();
                    } else if (mList.get(getLayoutPosition()).getTitleRes() == R.string.related_customer) {
                        Intent intent = new Intent(mActivity, CustomerActivity.class);
                        intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_ADD_SOMETHING_ACTIVITY);
                        mActivity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_CUSTOMER);
                        mLastClickPosBeforeLeave = getLayoutPosition();
                    } else if (mList.get(getLayoutPosition()).getTitleRes() == R.string.related_business_opportunity) {
                        if (mRelatedBusinessClickListener != null) {
                            mRelatedBusinessClickListener.onClick(v);
                            mLastClickPosBeforeLeave = getLayoutPosition();
                        }
                    } else if (mList.get(getLayoutPosition()).getTitleRes() == R.string.client_signed_person) {
                        if (mClientSignedClickListener != null) {
                            mClientSignedClickListener.onClick(v);
                            mLastClickPosBeforeLeave = getLayoutPosition();
                        }
                    } else if (mList.get(getLayoutPosition()).getTitleRes() == R.string.signed_person) {
                        if (mSignedClickListener != null) {
                            mSignedClickListener.onClick(v);
                            mLastClickPosBeforeLeave = getLayoutPosition();
                        }
                    } else {
                        final AddItem item = mList.get(getLayoutPosition());
                        if (item.getArrayRes() != 0 && item.getWebArrayRes() != 0) {
                            ShowWidgetUtil.showMultiItemDialog(mActivity, item.getTitleRes(), item.getArrayRes(), false, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CharSequence[] webArray = mActivity.getResources().getTextArray(item.getWebArrayRes());
                                    mList.get(getLayoutPosition()).setContent(webArray[which].toString());
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
                ShowWidgetUtil.showShort(mActivity.getResources().getString(R.string.please_complete_required, mActivity.getResources().getString(mList.get(pos).getTitleRes())));
                return false;
            }
        }
        if (mEmailPos != -1) {
            String email = mList.get(mEmailPos).getContent();
            if (!TextUtils.isEmpty(email) && !RegularUtil.isEmail(email)) {
                ShowWidgetUtil.showShort(mActivity.getResources().getString(R.string.email_wrong_message));
                return false;
            }
        }
        if (mMobilePos != -1) {
            String mobile = mList.get(mMobilePos).getContent();
            if (!TextUtils.isEmpty(mobile) && !RegularUtil.isMobile(mobile)) {
                ShowWidgetUtil.showShort(mActivity.getResources().getString(R.string.mobile_wrong_message));
                return false;
            }
        }
        if (mTelPos != -1) {
            String phone = mList.get(mTelPos).getContent();
            if (!TextUtils.isEmpty(phone) && !RegularUtil.isTel(phone)) {
                ShowWidgetUtil.showShort(mActivity.getResources().getString(R.string.phone_wrong_message));
                return false;
            }
        }
        return true;
    }

    public void setLastClickedName(String name) {
        mList.get(mLastClickPosBeforeLeave).setContent(name);
        notifyDataSetChanged();
    }

    //用户新建合同，选择完商机，客户自动填上名字
    public void setCustomerName(String name){
        mList.get(mRelatedCustomer).setContent(name);
        notifyDataSetChanged();
    }

    //点击关联商机
    View.OnClickListener mRelatedBusinessClickListener;
    //点击客户方签约人
    View.OnClickListener mClientSignedClickListener;
    //点击我方签约人
    View.OnClickListener mSignedClickListener;

    public void setOnRelatedBusinessClickListener(View.OnClickListener listener) {
        mRelatedBusinessClickListener = listener;
    }

    public void setOnClientSignedClickListener(View.OnClickListener listener) {
        mClientSignedClickListener = listener;
    }

    public void setOnSignedClickListener(View.OnClickListener listener) {
        mSignedClickListener = listener;
    }


}
