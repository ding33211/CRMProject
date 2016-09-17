package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-9.
 */
public class ContactSpecActivity extends ActivityPresenter<SpecActivityDelegate> {
    List<AddItem> mList;
    boolean hasTop;
    ContactParams mContactParams;

    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        mContactParams = (ContactParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTACT);
        initContactParams(mContactParams);
    }

    private void initContactParams(ContactParams contactParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contactParams.getName(), R.string.name, true);
        initItem(contactParams.getCustomer(), R.string.related_customer, hasTop ? false : true);
        initItem(contactParams.getPosition(), R.string.post, hasTop ? false : true);
        initItem(contactParams.getDepartment(), R.string.department, hasTop ? false : true);
        if(!hasTop){
            mList.remove(mList.size() - 1);
        } else {
            hasTop = false;
        }
        addItem = new AddItem();
        addItem.setTitleRes(R.string.contact_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contactParams.getPhone(), R.string.phone, true);
        initItem(contactParams.getMobile(), R.string.mobile, hasTop ? false : true);
        initItem(contactParams.getQq(), R.string.qq, hasTop ? false : true);
        initItem(contactParams.getWechat(), R.string.wechat, hasTop ? false : true);
        initItem(contactParams.getWangwang(), R.string.wangwang, hasTop ? false : true);
        initItem(contactParams.getEmail(), R.string.email, hasTop ? false : true);
        if(!hasTop){
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

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.contact_spec);
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContactParams[] params) {
        List<ContactParams> list = Arrays.asList(params);
        mContactParams = list.get(0);
        initContactParams(mContactParams);
    }

    /**
     * 请求clue失败
     *
     * @param t
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactSpecActivity.this, AddContactActivity.class);
                intent.putExtra(Contants.EXTRA_CONTACT, mContactParams);
                startActivity(intent);
            }
        });
    }


}
