package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
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
public class AddCustomerActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private CustomerParams mCustomerParams;

    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        mFromEdit = false;
        if (mCustomerParams != null) {
            mFromEdit = true;
            viewDelegate.setTitle(R.string.edit_customer);
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
                    if (mFromEdit) {
                        Map<String, String> map = CompileUtil.compile(mCustomerParams, getNewCustomerParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        if (map.size() > 0) {
                            RetrofitRequest.getInstance().updateCustomer(mCustomerParams.getId(), map);
                        }
                    } else {
                        RetrofitRequest.getInstance().addCustomer(getNewCustomerParams());
                    }
                    finish();
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
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
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
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_LOCATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contact_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.name);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getManager())) {
//            item.setContent(mCustomerParams.getManager());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.level);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.get)) {
//            item.setContent(mCustomerParams.getMobile());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.phone);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getQq())) {
//            item.setContent(mCustomerParams.getQq());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.mobile);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getQq())) {
//            item.setContent(mCustomerParams.getQq());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.qq);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getQq())) {
//            item.setContent(mCustomerParams.getQq());
//        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.email);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getEmail())) {
//            item.setContent(mCustomerParams.getEmail());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
//        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//        mList.add(item);
//        item = new AddItem();
//        item.setTitleRes(R.string.website);
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getWebsite())) {
//            item.setContent(mCustomerParams.getWebsite());
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
//        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getAddress())) {
//            item.setContent(mCustomerParams.getAddress());
//        }
//        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_LOCATE);
//        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.add_contact);
        item.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.other_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.customer_source);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getSource())) {
            item.setContent(mCustomerParams.getSource());
        }
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
            item.setContent(mCustomerParams.getSize());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);


        item = new AddItem();
        item.setTitleRes(R.string.manager_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.manager);
        if (mFromEdit && !TextUtils.isEmpty(mCustomerParams.getManager())) {
            item.setContent(mCustomerParams.getManager());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.in_department);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
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
                customerParams.setManager(item.getContent());
                continue;
            }
        }
        return customerParams;
    }
}
