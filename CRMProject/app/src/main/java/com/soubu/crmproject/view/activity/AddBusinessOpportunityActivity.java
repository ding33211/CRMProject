package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-29.
 */
public class AddBusinessOpportunityActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private BusinessOpportunityParams mBusinessOpportunityParams;
    private String mCustomerId;
    public static final int REQUEST_ADD_BUSINESS = 1002;

    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDelegate.verifyRequired()) {
                    if (mFromEdit) {
                        Map<String, String> map = CompileUtil.compile(mBusinessOpportunityParams, getNewBusinessOpportunityParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        if (map.size() > 0) {
                            RetrofitRequest.getInstance().updateBusinessOpportunity(mBusinessOpportunityParams.getId(), map);
                        }
                    } else {
                        RetrofitRequest.getInstance().addBusinessOpportunity(getNewBusinessOpportunityParams());
                    }
                    finish();
                } else {
                    ShowWidgetUtil.showLong(R.string.please_complete_required);
                }
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mBusinessOpportunityParams = (BusinessOpportunityParams) getIntent().getSerializableExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY);
        mFromEdit = false;
        if (mBusinessOpportunityParams != null) {
            mFromEdit = true;
            viewDelegate.setTitle(R.string.edit_business_opportunity);
        } else {
            viewDelegate.setTitle(R.string.add_business_opportunity);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        AddItem item = new AddItem();
        item.setTitleRes(R.string.essential_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_title);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getTitle())) {
            item.setContent(mBusinessOpportunityParams.getTitle());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_customer);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getCustomer())) {
            item.setContent(mBusinessOpportunityParams.getCustomer());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_product);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getProduct())) {
            item.setContent(mBusinessOpportunityParams.getProduct());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signing_amount);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getAmountPrice())) {
            item.setContent(mBusinessOpportunityParams.getAmountPrice());
        }
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.expected_time_to_sign);
        if (mFromEdit && mBusinessOpportunityParams.getClosingAt() != null) {
            item.setDate(mBusinessOpportunityParams.getClosingAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.actual_signing_time);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getGotAt())) {
            item.setContent(mBusinessOpportunityParams.getGotAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.contact_information);
//        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.phone);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getPhone())) {
//            item.setContent(mClueParams.getPhone());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
//        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.mobile);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getMobile())) {
//            item.setContent(mClueParams.getMobile());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.qq);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getQq())) {
//            item.setContent(mClueParams.getQq());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.email);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getEmail())) {
//            item.setContent(mClueParams.getEmail());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.website);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getWebsite())) {
//            item.setContent(mClueParams.getWebsite());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.area);
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.address);
//        if (mFromEdit && !TextUtils.isEmpty(mClueParams.getAddress())) {
//            item.setContent(mClueParams.getAddress());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_LOCATE);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.add_contact);
//        item.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_source);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getSource())) {
//            CharSequence[] array = SearchUtil.searchClueSourceArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchClueSourceWebArray(getApplicationContext());
            item.setContent(mBusinessOpportunityParams.getSource());
        }
        item.setArrayRes(R.array.clue_source);
        item.setWebArrayRes(R.array.clue_source_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_type);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getType())) {
//            CharSequence[] array = SearchUtil.searchBusinessOpportunityTypeArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchBusinessOpportunityTypeWebArray(getApplicationContext());
            item.setContent(mBusinessOpportunityParams.getType());
        }
        item.setArrayRes(R.array.business_opportunity_type);
        item.setWebArrayRes(R.array.business_opportunity_type_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_status);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getStatus())) {
//            CharSequence[] array = SearchUtil.searchBusinessOpportunityStateArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchBusinessOpportunityStateWebArray(getApplicationContext());
            item.setContent(mBusinessOpportunityParams.getStatus());
        }
        item.setArrayRes(R.array.business_opportunity_status);
        item.setWebArrayRes(R.array.business_opportunity_status_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.remark);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getNote())) {
            item.setContent(mBusinessOpportunityParams.getNote());
        }

        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.founder_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getManager())) {
            item.setContent(mBusinessOpportunityParams.getManager());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.in_department);
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
        viewDelegate.setData(mList);
    }

    public BusinessOpportunityParams getNewBusinessOpportunityParams() {
        List<AddItem> list = viewDelegate.getData();
        BusinessOpportunityParams businessOpportunityParams;
        if (mFromEdit) {
            try {
                businessOpportunityParams = mBusinessOpportunityParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            businessOpportunityParams = new BusinessOpportunityParams();
        }
        for (AddItem item : list) {
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || item.getItemType() == AddSomethingRvAdapter.TYPE_OTHER) {
                continue;
            }
            if (item.getTitleRes() == R.string.business_opportunity_title) {
                businessOpportunityParams.setTitle(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.related_customer && !TextUtils.isEmpty(mCustomerId)) {
                businessOpportunityParams.setCustomer(mCustomerId);
                continue;
            }
            if (item.getTitleRes() == R.string.related_product) {
                businessOpportunityParams.setProduct(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.signing_amount) {
                businessOpportunityParams.setAmountPrice(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.expected_time_to_sign) {
                businessOpportunityParams.setClosingAt(item.getDate());
                continue;
            }
            if (item.getTitleRes() == R.string.actual_signing_time) {
                businessOpportunityParams.setGotAt(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.business_opportunity_source) {
                businessOpportunityParams.setSource(item.getContent());
            }
            if (item.getTitleRes() == R.string.business_opportunity_type) {
                businessOpportunityParams.setType(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.business_opportunity_status) {
                businessOpportunityParams.setStatus(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.remark) {
                businessOpportunityParams.setNote(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.manager) {
                businessOpportunityParams.setManager(item.getContent());
                continue;
            }
        }
        return businessOpportunityParams;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_ADD_BUSINESS){
                mCustomerId = data.getStringExtra(Contants.EXTRA_CUSTOMER_ID);
                String name = data.getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
                viewDelegate.setCustomerName(name);
            }
        }
    }
}
