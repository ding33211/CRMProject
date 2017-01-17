package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.RegularUtil;
import com.soubu.crmproject.utils.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-29.
 */
public class AddBusinessOpportunityActivity extends Big4AddActivityPresenter {
    private List<AddItem> mList;
    private BusinessOpportunityParams mBusinessOpportunityParams;
    private String mCustomerId;
    private String mCustomerName;

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
                            mEventBusJustForThis = true;
                            RetrofitRequest.getInstance().updateBusinessOpportunity(mBusinessOpportunityParams.getId(), map);
                        } else {
                            finish();
                        }
                    } else {
                        mEventBusJustForThis = true;
                        RetrofitRequest.getInstance().addBusinessOpportunity(getNewBusinessOpportunityParams());
                    }
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
        mCustomerId = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID);
        mCustomerName = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
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
        if (!TextUtils.isEmpty(mCustomerName)) {
            item.setContent(mCustomerName);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
        } else {
            if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getCustomer().getName())) {
                item.setContent(mBusinessOpportunityParams.getCustomer().getName());
            }
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        }
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.related_product);
//        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getProduct())) {
//            item.setContent(mBusinessOpportunityParams.getProduct());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signing_amount);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getAmountPrice()) && TextUtils.equals(mBusinessOpportunityParams.getAmountPrice(), "0")) {
            item.setContent(mBusinessOpportunityParams.getAmountPrice());
        }
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.expected_time_to_sign);
        if (mFromEdit && mBusinessOpportunityParams.getClosingAt() != null) {
            item.setDate(mBusinessOpportunityParams.getClosingAt());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE_DATE);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.actual_signing_time);
//        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getGotAt())) {
//            item.setContent(mBusinessOpportunityParams.getGotAt());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_source);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getSource())) {
            item.setContent(mBusinessOpportunityParams.getSource());
        }
        item.setArrayRes(R.array.clue_source);
        item.setWebArrayRes(R.array.clue_source_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_type);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getType())) {
            item.setContent(mBusinessOpportunityParams.getType());
        }
        item.setArrayRes(R.array.business_opportunity_type);
        item.setWebArrayRes(R.array.business_opportunity_type_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_status);
        if (mFromEdit && !TextUtils.isEmpty(mBusinessOpportunityParams.getStatus())) {
            item.setContent(mBusinessOpportunityParams.getStatus());
        } else {
            item.setContent(SearchUtil.searchBusinessOpportunityStateWebArray(this)[0].toString());
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
        item.setTitleRes(R.string.manager_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && mBusinessOpportunityParams.getUser() != null && !TextUtils.isEmpty(mBusinessOpportunityParams.getUser().getUserName())) {
            item.setContent(mBusinessOpportunityParams.getUser().getUserName());
            mManagerId = mBusinessOpportunityParams.getUser().getId();
        } else if (mFromEdit && mBusinessOpportunityParams.getCreator() != null && !TextUtils.isEmpty(mBusinessOpportunityParams.getUser().getUserName())) {
            item.setContent(mBusinessOpportunityParams.getCreator().getUserName());
            mManagerId = mBusinessOpportunityParams.getCreator().getId();
        } else {
            item.setContent(CrmApplication.getContext().getName());
            mManagerId = CrmApplication.getContext().getUid();
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
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
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || TextUtils.isEmpty(item.getContent())) {
                continue;
            }
            if (item.getTitleRes() == R.string.business_opportunity_title) {
                businessOpportunityParams.setTitle(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.related_customer && !TextUtils.isEmpty(mCustomerId)) {
                businessOpportunityParams.setCustomerId(mCustomerId);
                continue;
            }
//            if (item.getTitleRes() == R.string.related_product) {
//                businessOpportunityParams.setProduct(item.getContent());
//                continue;
//            }
            if (item.getTitleRes() == R.string.signing_amount && RegularUtil.isInteger(item.getContent())) {
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
                if (!TextUtils.isEmpty(mManagerId)) {
                    businessOpportunityParams.setUserId(mManagerId);
                }
                continue;
            }
        }
        return businessOpportunityParams;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AddSomethingRvAdapter.REQUEST_CODE_CHOOSE_CUSTOMER) {
                mCustomerId = data.getStringExtra(Contants.EXTRA_CUSTOMER_ID);
                String name = data.getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
                viewDelegate.setLastClickName(name);
            }
        }
    }
}
