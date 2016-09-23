package com.soubu.crmproject.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddFollowActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dingsigang on 16-8-30.
 */
public class AddFollowActivity extends ActivityPresenter<AddFollowActivityDelegate> implements View.OnClickListener {
    public static final int TYPE_RECORD = 0x00;
    public static final int TYPE_PLAN = 0x01;

    private static final int REQUEST_CODE_CHANGE_OBJECT = 1001;
    private boolean mFromAddHome = false;
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
    private List<Contact> mContactsList;

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
                if(viewDelegate.verify(mFollowParams)){
                    mEventBusJustForThis = true;
                    RetrofitRequest.getInstance().addFollow(mFollowParams);
                }
            }
        });
        viewDelegate.setOnClickListener(this, R.id.rl_state, R.id.rl_follow_function, R.id.rl_follow_time, R.id.rl_remind_time, R.id.rl_transfer,
                R.id.rl_remind, R.id.ll_related_one, R.id.rl_expected_contract);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Contants.EXTRA_TYPE, TYPE_RECORD);
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
        mEntity = getIntent().getSerializableExtra(Contants.EXTRA_ENTITY);
        mFromAddHome = getIntent().getBooleanExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, false);
        mFollowParams = new FollowParams();
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
        if (mFromAddHome) {
            viewDelegate.get(R.id.iv_related_one).setVisibility(View.VISIBLE);
        }
        if(mType == TYPE_PLAN){
            mFollowParams.setType(Contants.FOLLOW_TYPE_PLAN);
        }
        ContactDao contactDao = DBHelper.getInstance(getApplicationContext()).getContactDao();
        mContactsList = new ArrayList<>();
        String state;
        switch (mFrom) {
            case Contants.FROM_CLUE:
                mStateLabelRes = R.string.clue_status;
                mStateArrayRes = R.array.clue_status;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.clue_status_web);
                ClueParams clueParams = (ClueParams) mEntity;
                mFollowParams.setEntityId(clueParams.getId());
                mFollowParams.setEntityType(Contants.FOLLOW_TYPE_OPPORTUNITY);
                state = mStateArray[SearchUtil.searchInArray(mStateArrayWeb, clueParams.getStatus())].toString();
                viewDelegate.giveTextViewString(R.id.tv_state, state);
                if(TextUtils.equals(clueParams.getStatus(), mStateArrayWeb[1])){
                    viewDelegate.get(R.id.rl_transfer).setVisibility(View.VISIBLE);
                }
                mFollowParams.setStatus(clueParams.getStatus());
                viewDelegate.giveTextViewString(R.id.tv_related_one, clueParams.getCompanyName());
                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.follow_clue));
                viewDelegate.get(R.id.rl_expected_contract).setVisibility(View.GONE);
                break;

            case Contants.FROM_CUSTOMER:
                CustomerParams customerParams = (CustomerParams) mEntity;
                mFollowParams.setEntityId(customerParams.getId());
                mFollowParams.setEntityType(Contants.FOLLOW_TYPE_CUSTOMER);
                viewDelegate.giveTextViewString(R.id.tv_related_one, customerParams.getName());
                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.follow_customer));
                viewDelegate.get(R.id.rl_state).setVisibility(View.GONE);
                mContactsList = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(customerParams.getId()))
                        .orderDesc(ContactDao.Properties.TouchedAt).list();
                return;

            case Contants.FROM_BUSINESS_OPPORTUNITY:
                mStateLabelRes = R.string.business_opportunity_status;
                mStateArrayRes = R.array.business_opportunity_status;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.business_opportunity_status_web);
                BusinessOpportunityParams businessOpportunityParams = (BusinessOpportunityParams) mEntity;
                mFollowParams.setEntityId(businessOpportunityParams.getId());
                mFollowParams.setEntityType(Contants.FOLLOW_TYPE_DEAL);
                state = mStateArray[SearchUtil.searchInArray(mStateArrayWeb, businessOpportunityParams.getStatus())].toString();
                viewDelegate.giveTextViewString(R.id.tv_state, state);
                if(TextUtils.equals(businessOpportunityParams.getStatus(), mStateArrayWeb[4])) {
                    viewDelegate.get(R.id.rl_transfer).setVisibility(View.VISIBLE);
                    viewDelegate.giveTextViewString(R.id.tv_transfer, getString(R.string.transfer_contract_now));
                }
                mFollowParams.setStatus(state);
                viewDelegate.giveTextViewString(R.id.tv_related_one, businessOpportunityParams.getTitle());
                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.follow_business_opportunity));
                mContactsList = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(businessOpportunityParams.getCustomer().getId()))
                        .orderDesc(ContactDao.Properties.TouchedAt).list();
                break;
            case Contants.FROM_CONTRACT:
                mStateLabelRes = R.string.contract_status;
                mStateArrayRes = R.array.contract_state;
                mStateArray = getResources().getTextArray(mStateArrayRes);
                mStateArrayWeb = getResources().getTextArray(R.array.contract_state_web);
                ContractParams contractParams = (ContractParams) mEntity;
                mFollowParams.setEntityId(contractParams.getId());
                mFollowParams.setEntityType(Contants.FOLLOW_TYPE_CONTRACT);
                state = mStateArray[SearchUtil.searchInArray(mStateArrayWeb, contractParams.getStatus())].toString();
                viewDelegate.giveTextViewString(R.id.tv_state, state);
                mFollowParams.setStatus(state);
//                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.related_contact));
                viewDelegate.giveTextViewString(R.id.tv_related_one, contractParams.getTitle());
                viewDelegate.giveTextViewString(R.id.tv_related_one_label, getString(R.string.follow_contract));
                mContactsList = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(contractParams.getCustomer().getId()))
                        .orderDesc(ContactDao.Properties.TouchedAt).list();
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
                        if(mFrom == Contants.FROM_CLUE && which == 1){
                            viewDelegate.get(R.id.rl_transfer).setVisibility(View.VISIBLE);
                        } else if(mFrom == Contants.FROM_BUSINESS_OPPORTUNITY && which == 4){
                            viewDelegate.get(R.id.rl_transfer).setVisibility(View.VISIBLE);
                            viewDelegate.giveTextViewString(R.id.tv_transfer, getString(R.string.transfer_contract_now));
                        } else {
                            viewDelegate.get(R.id.rl_transfer).setVisibility(View.GONE);
                        }
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
                        ((TextView)viewDelegate.get(R.id.tv_follow_method)).setTextColor(getResources().getColor(R.color.filter_tab_text_color));
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
                        ((TextView)viewDelegate.get(R.id.tv_follow_method)).setTextColor(getResources().getColor(R.color.filter_tab_text_color));
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
            case R.id.rl_transfer:
                viewDelegate.pressTransfer();
                break;
            case R.id.ll_related_one:
                if (mFromAddHome) {
                    Intent intent = new Intent(this, AddFollowHomeActivity.class);
                    intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW, true);
                    startActivityForResult(intent, REQUEST_CODE_CHANGE_OBJECT);
                }
                break;
            case R.id.rl_expected_contract:
                if(mContactsList.size() > 0){
                    String[] items = new String[mContactsList.size()];
                    for(int i = 0; i < mContactsList.size(); i ++){
                        items[i] = mContactsList.get(i).getName();
                    }
                    new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mFollowParams.setContactId(mContactsList.get(which).getContact_id());
                            ((TextView)viewDelegate.get(R.id.tv_expected_contract)).setText(mContactsList.get(which).getName());
                            ((TextView)viewDelegate.get(R.id.tv_follow_method)).setTextColor(getResources().getColor(R.color.filter_tab_text_color));
                        }
                    }).setCancelable(true).show();

                } else {
                    ShowWidgetUtil.showLong(R.string.customer_has_no_contact);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_CHANGE_OBJECT){
                mFrom = data.getIntExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
                mEntity = data.getSerializableExtra(Contants.EXTRA_ENTITY);
                initView();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFollow(FollowParams[] params) {
        if(params != null && params.length > 0){
            if(!mEventBusJustForThis){
                return;
            } else {
                mEventBusJustForThis = false;
            }
            ShowWidgetUtil.showShort(R.string.add_follow_success);
            finish();
        }
    }
}
