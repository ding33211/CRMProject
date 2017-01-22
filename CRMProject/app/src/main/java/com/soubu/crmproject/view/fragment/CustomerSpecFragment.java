package com.soubu.crmproject.view.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.CustomerSpecFragmentDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 17-1-17.
 */
public class CustomerSpecFragment extends FragmentPresenter<CustomerSpecFragmentDelegate> {
//    private CustomerParams mCustomerParams;
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
    boolean ifHighSeas = false;


    @Override
    protected Class<CustomerSpecFragmentDelegate> getDelegateClass() {
        return CustomerSpecFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        Context context = getActivity().getApplicationContext();
//        mCustomerParams = (CustomerParams) getActivity().getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        mSizeArray = SearchUtil.searchCustomerSizeArray(context);
        mSizeWebArray = SearchUtil.searchCustomerSizeWebArray(context);
        mTypeArray = SearchUtil.searchCustomerTypeArray(context);
        mTypeWebArray = SearchUtil.searchCustomerTypeWebArray(context);
        mPropertyArray = SearchUtil.searchCustomerPropertyArray(context);
        mPropertyWebArray = SearchUtil.searchCustomerPropertyWebArray(context);
        mSourceArray = SearchUtil.searchClueSourceArray(context);
        mSourceWebArray = SearchUtil.searchClueSourceWebArray(context);
        RetrofitRequest.getInstance().getCustomerSpec(getActivity().getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
//        initClueParams(mCustomerParams);
    }


    private void initClueParams(CustomerParams customerParams) {
        Log.e("xxxxxxxx", "customerParams    :    " + customerParams);
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(customerParams.getName(), R.string.customer_name, true);
//        initItem(TextUtils.isEmpty(customerParams.getType()) ? "" : mTypeArray[SearchUtil.searchInArray(mTypeWebArray, customerParams.getType())].toString(), R.string.customer_type, hasTop ? false : true);
        initItem(TextUtils.isEmpty(customerParams.getProperty()) ? "" : mPropertyArray[SearchUtil.searchInArray(mPropertyWebArray, customerParams.getProperty())].toString(), R.string.customer_property, hasTop ? false : true);
//        initItem(customerParams.getWebsite(), R.string.website, hasTop ? false : true);
        initItem(customerParams.getBusinessScope(), R.string.business_scope, !hasTop);
        initItem(customerParams.getAddress(), R.string.address, !hasTop);
        if (!hasTop) {
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
        addItem.setTitleRes(R.string.contact_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(customerParams.getContactName(), R.string.contact_name, !hasTop);
        initItem(customerParams.getPosition(), R.string.contact_position, !hasTop);
        initItem(customerParams.getMobile(), R.string.contact_mobile, !hasTop);

//        initItem(customerParams.getContactName(), R.string.contact_name, hasTop ? false : true);
//        initItem(customerParams.getContactName(), R.string.contact_name, hasTop ? false : true);

//        initItem(customerParams.getStatus(), R.string.follow_state, true);
//        if (customerParams.getProducts() != null && customerParams.getProducts().length != 0) {
//            initItem(customerParams.getProducts()[0], R.string.operating_products, true);
//        }
//        initItem(TextUtils.isEmpty(customerParams.getSource()) ? "" : mSourceArray[SearchUtil.searchInArray(mSourceWebArray, customerParams.getSource())].toString(), R.string.customer_source, hasTop ? false : true);

//        initItem(TextUtils.isEmpty(customerParams.getSize()) ? "" : mSizeArray[SearchUtil.searchInArray(mSizeWebArray, customerParams.getSize())].toString(), R.string.personal_size, hasTop ? false : true);
//        hasTop = false;
//        addItem = new AddItem();
//        addItem.setTitleRes(R.string.founder_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        initItem(customerParams.getManager(), R.string.manager, true);
//        addItem = new AddItem();
//        addItem.setTitleRes(R.string.manager_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        if (customerParams.getUser() != null && !TextUtils.isEmpty(customerParams.getUser().getUsername())) {
//            initItem(customerParams.getUser().getUsername(), R.string.manager, true);
//        } else if (customerParams.getCreator() != null && !TextUtils.isEmpty(customerParams.getCreator().getUsername())) {
//            initItem(customerParams.getCreator().getUsername(), R.string.manager, true);
//        } else {
//            initItem(CrmApplication.getContext().getName(), R.string.manager, true);
//        }
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

//    @Override
//    protected void initToolbar() {
//        super.initToolbar();
//        viewDelegate.setTitle(R.string.customer_spec);
//        ifHighSeas = getIntent().getIntExtra(Contants.EXTRA_FROM, -1) == Contants.FROM_CUSTOMER_HIGH_SEAS;
//    }


    public void setIfHighSeas(boolean ifHighSeas){
        this.ifHighSeas = ifHighSeas;
    }

//    @Override
//    protected void bindEvenListener() {
//        super.bindEvenListener();
//        if (!ifHighSeas) {
//            viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
//                    intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
//                    startActivity(intent);
//                }
//            });
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams params) {
//        List<CustomerParams> list = Arrays.asList(params);
//        mCustomerParams = list.get(0);
        initClueParams(params);
    }


}
