package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class CustomerSpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    private CustomerParams mCustomerParams;
    List<AddItem> mList;
    boolean hasTop;
    CharSequence[] mSizeArray;
    CharSequence[] mSizeWebArray;
    CharSequence[] mTypeArray;
    CharSequence[] mTypeWebArray;
    CharSequence[] mPropertyArray;
    CharSequence[] mPropertyWebArray;
    CharSequence[] mSourceArray;
    CharSequence[] mSourceWebArray;


    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        mSizeArray = SearchUtil.searchCustomerSizeArray(getApplicationContext());
        mSizeWebArray = SearchUtil.searchCustomerSizeWebArray(getApplicationContext());
        mTypeArray = SearchUtil.searchCustomerTypeArray(getApplicationContext());
        mTypeWebArray = SearchUtil.searchCustomerTypeWebArray(getApplicationContext());
        mPropertyArray = SearchUtil.searchCustomerPropertyArray(getApplicationContext());
        mPropertyWebArray = SearchUtil.searchCustomerPropertyWebArray(getApplicationContext());
        mSourceArray = SearchUtil.searchClueSourceArray(getApplicationContext());
        mSourceWebArray = SearchUtil.searchClueSourceWebArray(getApplicationContext());
        initClueParams(mCustomerParams);
    }


    private void initClueParams(CustomerParams customerParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(customerParams.getName(), R.string.customer_name, true);
        initItem(TextUtils.isEmpty(customerParams.getType()) ? "" : mTypeArray[SearchUtil.searchInArray(mTypeWebArray, customerParams.getType())].toString(), R.string.customer_type, hasTop ? false : true);
        initItem(TextUtils.isEmpty(customerParams.getProperty()) ? "" : mPropertyArray[SearchUtil.searchInArray(mPropertyWebArray, customerParams.getProperty())].toString(), R.string.customer_property, hasTop ? false : true);
        initItem(customerParams.getWebsite(), R.string.website, hasTop ? false : true);
        initItem(customerParams.getAddress(), R.string.address, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        //        addItem = new AddItem();
//        addItem.setTitleRes(R.string.contact_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        initItem(customerParams.getPhone(), R.string.phone, true);
////        initItem(customerParams.getMobile(), R.string.mobile, hasTop ? false : true);
////        initItem(customerParams.getQq(), R.string.qq, hasTop ? false : true);
////        initItem(customerParams.getWechat(), R.string.wechat, hasTop ? false : true);
////        initItem(customerParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
//        initItem(customerParams.getEmail(), R.string.email, hasTop ? false : true);
//        initItem(customerParams.getPostcode(), R.string.postcode, hasTop ? false : true);
//        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.other_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
//        initItem(customerParams.getStatus(), R.string.follow_state, true);
        if (customerParams.getProducts() != null && customerParams.getProducts().length != 0) {
            initItem(customerParams.getProducts()[0], R.string.operating_products, true);
        }
        initItem(TextUtils.isEmpty(customerParams.getSource()) ? "" : mSourceArray[SearchUtil.searchInArray(mSourceWebArray, customerParams.getSource())].toString(), R.string.customer_source, hasTop ? false : true);

        initItem(TextUtils.isEmpty(customerParams.getSize()) ? "" : mSizeArray[SearchUtil.searchInArray(mSizeWebArray, customerParams.getSize())].toString(), R.string.personal_size, hasTop ? false : true);
//        hasTop = false;
//        addItem = new AddItem();
//        addItem.setTitleRes(R.string.founder_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        initItem(customerParams.getManager(), R.string.manager, true);
        addItem = new AddItem();
        addItem.setTitleRes(R.string.manager_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        if(customerParams.getUser() != null && !TextUtils.isEmpty(customerParams.getUser().getUserName())){
            initItem(customerParams.getUser().getUserName(), R.string.manager, true);
        } else if(customerParams.getCreator() != null && !TextUtils.isEmpty(customerParams.getCreator().getUserName())){
            initItem(customerParams.getCreator().getUserName(), R.string.manager, true);
        } else {
            initItem(CrmApplication.getContext().getName(), R.string.manager, true);
        }
        viewDelegate.setData(mList);
    }


    public void initItem(String content, int labelRes, boolean isTop) {
        if (!TextUtils.isEmpty(content) && !TextUtils.equals(content, "0")) {
            if (!isTop) {
                mList.get(mList.size() - 1).setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
            } else {
                hasTop = true;
            }
            AddItem addItem = new AddItem();
            addItem.setTitleRes(labelRes);
            addItem.setContent(content);
            addItem.setItemType(AddSomethingRvAdapter.TYPE_OTHER);
            mList.add(addItem);
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.customer_spec);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerSpecActivity.this, AddCustomerActivity.class);
                intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        List<CustomerParams> list = Arrays.asList(params);
        mCustomerParams = list.get(0);
        initClueParams(mCustomerParams);
    }

}
