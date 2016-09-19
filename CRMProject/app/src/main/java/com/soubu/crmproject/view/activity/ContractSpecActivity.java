package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class ContractSpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    List<AddItem> mList;
    boolean hasTop;
    ContractParams mContractParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateWebArray;
    CharSequence[] mPayMethodArray;
    CharSequence[] mPayMethodWebArray;
    CharSequence[] mTypeArray;
    CharSequence[] mTypeWebArray;

    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        mStateArray = SearchUtil.searchContractStateArray(getApplicationContext());
        mStateWebArray = SearchUtil.searchContractStateWebArray(getApplicationContext());
        mPayMethodArray = SearchUtil.searchContractPayMethodArray(getApplicationContext());
        mPayMethodWebArray = SearchUtil.searchContractPayMethodWebArray(getApplicationContext());
        mTypeArray = SearchUtil.searchContractTypeArray(getApplicationContext());
        mTypeWebArray = SearchUtil.searchContractTypeWebArray(getApplicationContext());
        initContractParams(mContractParams);
    }

    private void initContractParams(ContractParams contractParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contractParams.getTitle(), R.string.business_opportunity_title, true);
        initItem(contractParams.getCustomer(), R.string.related_customer, hasTop ? false : true);
        initItem(contractParams.getDeal(), R.string.related_business_opportunity, hasTop ? false : true);
        initItem(contractParams.getProduct(), R.string.related_product, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.contact_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(TextUtils.isEmpty(contractParams.getType()) ? "" : mTypeArray[SearchUtil.searchInArray(mTypeWebArray, contractParams.getType())].toString(),
                R.string.contract_type, true);
        initItem(contractParams.getSerialNumber(), R.string.contract_serial_number, hasTop ? false : true);
        initItem(contractParams.getAmountPrice(), R.string.contract_amount_price, hasTop ? false : true);
        initItem(TextUtils.isEmpty(contractParams.getStatus()) ? "" : mStateArray[SearchUtil.searchInArray(mStateWebArray, contractParams.getStatus())].toString(),
                R.string.contract_status, hasTop ? false : true);
        initItem(ConvertUtil.dateToYYYY_MM_DD(contractParams.getStartedAt()), R.string.start_date, hasTop ? false : true);
        initItem(ConvertUtil.dateToYYYY_MM_DD(contractParams.getFinishedAt()), R.string.finish_date, hasTop ? false : true);
        initItem(TextUtils.isEmpty(contractParams.getPayMethod()) ? "" : mPayMethodArray[SearchUtil.searchInArray(mPayMethodWebArray, contractParams.getPayMethod())].toString(),
                R.string.pay_method, hasTop ? false : true);
        initItem(contractParams.getClientSignedPerson(), R.string.client_signed_person, hasTop ? false : true);
        initItem(contractParams.getSignedPerson(), R.string.signed_person, hasTop ? false : true);
        initItem(ConvertUtil.dateToYYYY_MM_DD(contractParams.getClosedAt()), R.string.signed_date, hasTop ? false : true);
//        initItem(contractParams.getAttachments().toString(), R.string.contract_attachments, hasTop ? false : true);
        initItem(contractParams.getNote(), R.string.remark, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        //        addItem = new AddItem();
//        addItem.setTitleRes(R.string.other_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        initItem(contractParams.getSource(), R.string.business_opportunity_source, true);
//        initItem(contractParams.getType(), R.string.business_opportunity_type, hasTop ? false : true);
//        initItem(contractParams.getStatus(), R.string.business_opportunity_status, hasTop ? false : true);
//        initItem(contractParams.getNote(), R.string.remark, hasTop ? false : true);
//        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.founder_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contractParams.getManager(), R.string.manager, true);
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
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractSpecActivity.this, AddContractActivity.class);
                intent.putExtra(Contants.EXTRA_CONTRACT, mContractParams);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.contract_spec);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        mContractParams = list.get(0);
        initContractParams(mContractParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
    }
}
