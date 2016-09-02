package com.soubu.crmproject.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddFollowActivityDelegate;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-30.
 */
public class AddFollowActivity extends ActivityPresenter<AddFollowActivityDelegate> implements View.OnClickListener {
    public static final int TYPE_RECORD = 0x00;
    public static final int TYPE_PLAN = 0x01;
    private int mType;
    private int mFrom;
    private int mStateLabelRes;
    private int mStateArrayRes;
    private CharSequence[] mStateArray;
    private CharSequence[] mStateArrayWeb;
    private CharSequence[] mFollowMethodArray;
    private CharSequence[] mFollowMethodArrayWeb;
    private Serializable mEntity;
    private FollowParams mFollowParams;
    private Calendar mFollowTime;

    @Override
    protected Class<AddFollowActivityDelegate> getDelegateClass() {
        return AddFollowActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDelegate.verify(mFollowParams);
                RetrofitRequest.getInstance().addFollow(mFollowParams);
                finish();
            }
        });
        viewDelegate.setOnClickListener(this, R.id.rl_state, R.id.rl_follow_function, R.id.rl_follow_time, R.id.rl_remind_time,
                R.id.rl_remind);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Contants.EXTRA_TYPE, TYPE_RECORD);
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, Big4HomeActivityDelegate.FROM_CLUE);
        mEntity = getIntent().getSerializableExtra(Contants.EXTRA_ENTITY);
        if (mType == TYPE_RECORD) {
            viewDelegate.setTitle(R.string.add_follow_record);
        } else {
            viewDelegate.setTitle(R.string.add_follow_plan);
        }
        viewDelegate.setTypeAndFrom(mType);
    }

    @Override
    protected void initView() {
        super.initView();
        mFollowParams = new FollowParams();
        switch (mFrom){
            case Big4HomeActivityDelegate.FROM_CLUE:
                mStateLabelRes = R.string.clue_status;
                mStateArrayRes = R.array.clue_status;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.clue_status_web);
                ClueParams clueParams = (ClueParams) mEntity;
                mFollowParams.setEntity(clueParams.getId());
                viewDelegate.giveTextViewString(R.id.tv_state, mStateArray[SearchUtil.searchInArray(mStateArrayWeb, clueParams.getStatus())].toString());
                viewDelegate.giveTextViewString(R.id.tv_related_one, clueParams.getCompanyName());
                break;

            case Big4HomeActivityDelegate.FROM_CUSTOMER:
                CustomerParams customerParams = (CustomerParams) mEntity;
                mFollowParams.setEntity(customerParams.getId());
                viewDelegate.giveTextViewString(R.id.tv_related_one, customerParams.getName());
                viewDelegate.get(R.id.rl_state).setVisibility(View.GONE);
                return;

            case Big4HomeActivityDelegate.FROM_BUSINESS_OPPORTUNITY:
                mStateLabelRes = R.string.business_opportunity_status;
                mStateArrayRes = R.array.business_opportunity_status;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.business_opportunity_status_web);
                BusinessOpportunityParams businessOpportunityParams = (BusinessOpportunityParams)mEntity;
                mFollowParams.setEntity(businessOpportunityParams.getId());
                viewDelegate.giveTextViewString(R.id.tv_state, mStateArray[SearchUtil.searchInArray(mStateArrayWeb, businessOpportunityParams.getStatus())].toString());
                viewDelegate.giveTextViewString(R.id.tv_related_one, businessOpportunityParams.getTitle());
                break;
            case Big4HomeActivityDelegate.FROM_CONTRACT:
                mStateLabelRes = R.string.contract_status;
                mStateArrayRes = R.array.contract_state;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.contract_state_web);
                ContractParams contractParams = (ContractParams)mEntity;
                mFollowParams.setEntity(contractParams.getId());
                viewDelegate.giveTextViewString(R.id.tv_state, mStateArray[SearchUtil.searchInArray(mStateArrayWeb, contractParams.getStatus())].toString());
//                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.related_contact));
                viewDelegate.giveTextViewString(R.id.tv_related_one, contractParams.getTitle());
//                viewDelegate.giveTextViewString(R.id.tv_related_two, contractParams.getCustomer());
                break;
        }
        viewDelegate.giveTextViewString(R.id.tv_state_label, getString(mStateLabelRes));
    }

    @Override
    protected void initData() {
        super.initData();
        mFollowTime = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        mFollowMethodArray = getResources().getStringArray(R.array.follow_method);
        mFollowMethodArrayWeb = getResources().getStringArray(R.array.follow_method_web);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_state:
                ShowWidgetUtil.showMultiItemDialog(this, mStateLabelRes, mStateArrayRes, false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFollowParams.setStatus(mStateArrayWeb[which].toString());
                        viewDelegate.giveTextViewString(R.id.tv_state, mStateArray[which].toString());
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.rl_follow_function:
                ShowWidgetUtil.showMultiItemDialog(this, R.string.follow_method, R.array.follow_method, false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFollowParams.setMethod(mFollowMethodArrayWeb[which].toString());
                        viewDelegate.giveTextViewString(R.id.tv_follow_method, mFollowMethodArray[which].toString());
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.rl_remind_time:
            case R.id.rl_follow_time:
                final Calendar now = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mFollowTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mFollowTime.set(Calendar.MINUTE, minute);
                        if (mType == TYPE_RECORD) {
                            if (mFollowTime.getTimeInMillis() > now.getTimeInMillis()) {
                                ShowWidgetUtil.showLong(R.string.add_follow_record_time_error);
                                mFollowTime = now;
                                return;
                            }
                        } else {
                            if (mFollowTime.getTimeInMillis() < now.getTimeInMillis()) {
                                ShowWidgetUtil.showLong(R.string.add_follow_plan_time_error);
                                mFollowTime = now;
                                return;
                            }
                        }
                        mFollowParams.setFollowupAt(mFollowTime.getTime());
                        viewDelegate.giveTextViewString(R.id.tv_follow_time, ConvertUtil.dateToYYYY_MM_DD_HH_mm(mFollowTime.getTime()));
                    }
                }, mFollowTime.get(Calendar.HOUR_OF_DAY), mFollowTime.get(Calendar.MINUTE), true);
                new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mFollowTime.set(year, monthOfYear, dayOfMonth);
                                if (mType == TYPE_RECORD) {
                                    if (mFollowTime.getTimeInMillis() > now.getTimeInMillis()) {
                                        ShowWidgetUtil.showLong(R.string.add_follow_record_time_error);
                                        mFollowTime = now;
                                        return;
                                    }
                                } else {
                                    if (mFollowTime.getTimeInMillis() < now.getTimeInMillis()) {
                                        ShowWidgetUtil.showLong(R.string.add_follow_plan_time_error);
                                        mFollowTime = now;
                                        return;
                                    }
                                }
                                timePickerDialog.show();
                            }
                        }
                        , mFollowTime.get(Calendar.YEAR), mFollowTime.get(Calendar.MONTH), mFollowTime
                        .get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rl_remind:
                viewDelegate.pressRemind();
                break;
        }
    }
}
