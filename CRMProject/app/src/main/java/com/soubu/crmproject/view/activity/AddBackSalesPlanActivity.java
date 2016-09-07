package com.soubu.crmproject.view.activity;

import android.text.InputType;
import android.text.TextUtils;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddSomethingActivityDelegate;
import com.soubu.crmproject.model.AddItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-9-7.
 */
public class AddBackSalesPlanActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;


    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.add_sales_back_plan);
    }


    @Override
    protected void initData() {
        super.initData();
        mList = new ArrayList<>();
        AddItem item = new AddItem();
        item.setTitleRes(R.string.sales_back_plan);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.business_opportunity_title);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_customer);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_product);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.signing_amount);
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.expected_time_to_sign);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_CHOOSE_DATE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.actual_signing_time);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        viewDelegate.setData(mList);
    }
}
