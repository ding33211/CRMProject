package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-26.
 */
public class AddCustomerActivity extends Big4AddActivityPresenter {
    private List<AddItem> mList;
    private CustomerParams mCustomerParams;
    //是否是线索转客户
    private boolean mTransfer;

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        mTransfer = getIntent().getBooleanExtra(Contants.EXTRA_TRANSFER, false);
        mFromEdit = false;
        if (mCustomerParams != null) {
            mFromEdit = true;
            if(mTransfer){
                viewDelegate.setTitle(R.string.transfer_customer);
            } else {
                viewDelegate.setTitle(R.string.edit_customer);
            }
        } else {
            viewDelegate.setTitle(R.string.add_customer);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewDelegate.verifyRequired()) {
                    if (mFromEdit && !mTransfer) {
                        Map<String, String> map = CompileUtil.compile(mCustomerParams, getNewCustomerParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        if (map.size() > 0) {
                            RetrofitRequest.getInstance().updateCustomer(mCustomerParams.getId(), map);
                        }
                    } else {
                        RetrofitRequest.getInstance().addCustomer(getNewCustomerParams());
                    }
                } else {
                    ShowWidgetUtil.showLong(R.string.please_complete_required);
                }
            }
        });
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
        item.setTitleRes(R.string.customer_name);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getName())) {
            item.setContent(mCustomerParams.getName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.customer_type);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getType())) {
            item.setContent(mCustomerParams.getType());
        }
        item.setArrayRes(R.array.customer_type);
        item.setWebArrayRes(R.array.customer_type_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.customer_property);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getProperty())) {
            item.setContent(mCustomerParams.getProperty());
        }
        item.setArrayRes(R.array.customer_property);
        item.setWebArrayRes(R.array.customer_property_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.website);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getWebsite())) {
            item.setContent(mCustomerParams.getWebsite());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.address);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getAddress())) {
            item.setContent(mCustomerParams.getAddress());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        if (!mFromEdit) {
            item = new AddItem();
            item.setTitleRes(R.string.contact_information);
            item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
            mList.add(item);
            item = new AddItem();
            item.setTitleRes(R.string.name);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
            mList.add(item);
            item = new AddItem();
            item.setTitleRes(R.string.level);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
            mList.add(item);
            item = new AddItem();
            item.setTitleRes(R.string.mobile);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
            item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
            mList.add(item);
            item = new AddItem();
            item.setTitleRes(R.string.phone);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
            item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
            mList.add(item);

            item = new AddItem();
            item.setTitleRes(R.string.qq);
            item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
            item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
            mList.add(item);
        }
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.customer_source);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getSource())) {
//            CharSequence[] array = SearchUtil.searchClueSourceArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchClueSourceWebArray(getApplicationContext());
            item.setContent(mCustomerParams.getSource());
        }
        item.setArrayRes(R.array.clue_source);
        item.setWebArrayRes(R.array.clue_source_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.operating_products);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getProducts())) {
//            item.setContent(mCustomerParams.getProducts());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.personal_size);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getSize())) {
//            CharSequence[] array = SearchUtil.searchCustomerSizeArray(getApplicationContext());
//            CharSequence[] webArray = SearchUtil.searchCustomerSizeWebArray(getApplicationContext());
            item.setContent(mCustomerParams.getSize());
        }
        item.setArrayRes(R.array.customer_size);
        item.setWebArrayRes(R.array.customer_size_web);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);

        item = new AddItem();
        item.setTitleRes(R.string.manager_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && mCustomerParams.getUser() != null && !TextUtils.isEmpty(mCustomerParams.getUser().getUserName())) {
            item.setContent(mCustomerParams.getUser().getUserName());
            mManagerId = mCustomerParams.getUser().getId();
        } else if(mFromEdit && mCustomerParams.getCreator() != null && !TextUtils.isEmpty(mCustomerParams.getUser().getUserName())){
            item.setContent(mCustomerParams.getCreator().getUserName());
            mManagerId = mCustomerParams.getCreator().getId();
        } else {
            item.setContent(CrmApplication.getContext().getName());
            mManagerId = CrmApplication.getContext().getUid();
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.in_department);
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
//        mList.add(item);
        viewDelegate.setData(mList);
    }


    public CustomerParams getNewCustomerParams() {
        List<AddItem> list = viewDelegate.getData();
        CustomerParams customerParams;
        if (mFromEdit) {
            try {
                customerParams = mCustomerParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            customerParams = new CustomerParams();
        }
        for (AddItem item : list) {
            if (item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL || item.getItemType() == AddSomethingRvAdapter.TYPE_OTHER) {
                continue;
            }
            if (item.getTitleRes() == R.string.customer_name) {
                customerParams.setName(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.customer_type) {
                customerParams.setType(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.customer_property) {
                customerParams.setProperty(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.website) {
                customerParams.setWebsite(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.address) {
                customerParams.setAddress(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.name) {
//                customerParams.setPhone(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.mobile) {
//                customerParams.setMobile(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.qq) {
//                customerParams.setQq(item.getContent());
            }
            if (item.getTitleRes() == R.string.email) {
//                customerParams.setEmail(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.website) {
//                customerParams.setWebsite(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.customer_source) {
                customerParams.setSource(item.getContent());
                continue;
            }
//            if(item.getTitleRes() == R.string.operating_products){
//                customerParams.setProducts(item.getContent());
//                continue;
//            }
            if (item.getTitleRes() == R.string.personal_size) {
                customerParams.setSize(item.getContent());
                continue;
            }
            if (item.getTitleRes() == R.string.manager) {
                if(!TextUtils.isEmpty(mManagerId)){
                    customerParams.setUserId(mManagerId);
                }
                continue;
            }
        }
        return customerParams;
    }
}
