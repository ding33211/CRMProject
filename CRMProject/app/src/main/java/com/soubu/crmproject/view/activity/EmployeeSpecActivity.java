package com.soubu.crmproject.view.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.greendao.Staff;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-17.
 */
public class EmployeeSpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    List<AddItem> mList;
    List<String> mPhoneList;
    boolean hasTop;
    private Staff mStaff;


    @Override
    protected Class getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_employee_spec, null);
        viewDelegate.addHeaderView(headerView);
    }

    @Override
    protected void initData() {
        super.initData();
        mPhoneList = new ArrayList<>();
        mStaff = (Staff) getIntent().getSerializableExtra(Contants.EXTRA_EMPLOYEE);
//        initConnectionMethod(mStaff);
//        initContactParams(mStaff);
    }

//    private void initConnectionMethod(Staff staff) {
//        if (TextUtils.isEmpty(params.getPhone()) && TextUtils.isEmpty(params.getMobile())) {
//            viewDelegate.get(R.id.iv_sns1).setAlpha(0.3f);
//        } else {
//            if (!TextUtils.isEmpty(params.getMobile())) {
//                mPhoneList.add(params.getMobile());
//            }
//            if (!TextUtils.isEmpty(params.getPhone())) {
//                mPhoneList.add(params.getPhone());
//            }
//            viewDelegate.get(R.id.iv_sns1).setAlpha(1);
//        }
//        if (TextUtils.isEmpty(params.getWechat())) {
//            viewDelegate.get(R.id.iv_sns2).setAlpha(0.3f);
//        } else {
//            viewDelegate.get(R.id.iv_sns2).setAlpha(1);
//        }
//        if (TextUtils.isEmpty(params.getEmail())) {
//            viewDelegate.get(R.id.iv_sns3).setAlpha(0.3f);
//        } else {
//            viewDelegate.get(R.id.iv_sns3).setAlpha(1);
//        }
//        if (TextUtils.isEmpty(params.getPhone()) && TextUtils.isEmpty(params.getMobile())) {
//            viewDelegate.get(R.id.iv_sns4).setAlpha(0.3f);
//        } else {
//            viewDelegate.get(R.id.iv_sns4).setAlpha(1);
//        }
//        if (TextUtils.isEmpty(params.getQq())) {
//            viewDelegate.get(R.id.iv_sns5).setAlpha(0.3f);
//        } else {
//            viewDelegate.get(R.id.iv_sns5).setAlpha(1);
//        }
//    }

    private void initContactParams(ContactParams contactParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contactParams.getName(), R.string.name, true);
        if (contactParams.getCustomer() != null) {
            initItem(contactParams.getCustomer().getName(), R.string.related_customer, hasTop ? false : true);
        }
        initItem(contactParams.getPosition(), R.string.post, hasTop ? false : true);
        initItem(contactParams.getDepartment(), R.string.department, hasTop ? false : true);
        if (!hasTop) {
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.connection_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contactParams.getPhone(), R.string.phone, true);
        initItem(contactParams.getMobile(), R.string.mobile, hasTop ? false : true);
        initItem(contactParams.getQq(), R.string.qq, hasTop ? false : true);
        initItem(contactParams.getWechat(), R.string.wechat, hasTop ? false : true);
        initItem(contactParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
        initItem(contactParams.getEmail(), R.string.email, hasTop ? false : true);
        if (!hasTop) {
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
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
}
