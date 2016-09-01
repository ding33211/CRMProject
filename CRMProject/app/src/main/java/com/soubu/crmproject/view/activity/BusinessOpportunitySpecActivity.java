package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.ConvertUtil;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class BusinessOpportunitySpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    List<AddItem> mList;
    boolean hasTop;
    BusinessOpportunityParams mBusinessOpportunityParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateWebArray;
    CharSequence[] mSourceArray;
    CharSequence[] mSourceWebArray;
    CharSequence[] mTypeArray;
    CharSequence[] mTypeWebArray;

    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mBusinessOpportunityParams = (BusinessOpportunityParams) getIntent().getSerializableExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY);
        mStateArray = SearchUtil.searchBusinessOpportunityStateArray(getApplicationContext());
        mStateWebArray = SearchUtil.searchBusinessOpportunityStateWebArray(getApplicationContext());
        mSourceArray = SearchUtil.searchClueSourceArray(getApplicationContext());
        mSourceWebArray = SearchUtil.searchClueStateWebArray(getApplicationContext());
        mTypeArray = SearchUtil.searchBusinessOpportunityTypeArray(getApplicationContext());
        mTypeWebArray = SearchUtil.searchBusinessOpportunityTypeWebArray(getApplicationContext());
        initBusinessOpportunityParams(mBusinessOpportunityParams);
    }


    private void initBusinessOpportunityParams(BusinessOpportunityParams businessOpportunityParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(businessOpportunityParams.getTitle(), R.string.business_opportunity_title, true);
        initItem(businessOpportunityParams.getCustomer(), R.string.related_customer, hasTop ? false : true);
        initItem(businessOpportunityParams.getProduct(), R.string.related_product, hasTop ? false : true);
        initItem(businessOpportunityParams.getAmountPrice(), R.string.signing_amount, hasTop ? false : true);
        initItem(ConvertUtil.dateToYYYY_MM_DD(businessOpportunityParams.getClosingAt()), R.string.expected_time_to_sign, hasTop ? false : true);
        initItem(businessOpportunityParams.getGotAt(), R.string.actual_signing_time, hasTop ? false : true);
        hasTop = false;
//        addItem = new AddItem();
//        addItem.setTitleRes(R.string.contact_information);
//        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
//        mList.add(addItem);
//        initItem(businessOpportunityParams.getPhone(), R.string.phone, true);
//        initItem(businessOpportunityParams.getMobile(), R.string.mobile, hasTop ? false : true);
//        initItem(businessOpportunityParams.getQq(), R.string.qq, hasTop ? false : true);
//        initItem(businessOpportunityParams.getWechat(), R.string.wechat, hasTop ? false : true);
//        initItem(businessOpportunityParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
//        initItem(businessOpportunityParams.getEmail(), R.string.email, hasTop ? false : true);
//        initItem(businessOpportunityParams.getWebsite(), R.string.website, hasTop ? false : true);
//        initItem(businessOpportunityParams.getAddress(), R.string.address, hasTop ? false : true);
//        initItem(businessOpportunityParams.getPostcode(), R.string.postcode, hasTop ? false : true);
//        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.other_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(TextUtils.isEmpty(businessOpportunityParams.getSource()) ? "" : mSourceArray[SearchUtil.searchInArray(mSourceWebArray, businessOpportunityParams.getSource())].toString()
                , R.string.business_opportunity_source, true);
        initItem(TextUtils.isEmpty(businessOpportunityParams.getType()) ? "" : mTypeArray[SearchUtil.searchInArray(mTypeWebArray, businessOpportunityParams.getType())].toString()
                , R.string.business_opportunity_type, hasTop ? false : true);
        initItem(TextUtils.isEmpty(businessOpportunityParams.getStatus()) ? "" : mStateArray[SearchUtil.searchInArray(mStateWebArray, businessOpportunityParams.getStatus())].toString()
                , R.string.business_opportunity_status, hasTop ? false : true);
        initItem(businessOpportunityParams.getNote(), R.string.remark, hasTop ? false : true);
        hasTop = false;
        addItem = new AddItem();
        addItem.setTitleRes(R.string.founder_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(businessOpportunityParams.getManager(), R.string.manager, true);
        viewDelegate.setData(mList);
    }


    public void initItem(String content, int labelRes, boolean isTop) {
        if (!TextUtils.isEmpty(content)) {
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
                Intent intent = new Intent(BusinessOpportunitySpecActivity.this, AddBusinessOpportunityActivity.class);
                intent.putExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY, mBusinessOpportunityParams);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.business_opportunity_spec);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<BusinessOpportunityParams> list) {
        initBusinessOpportunityParams(list.get(0));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }

}
