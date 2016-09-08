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
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.CompileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-9-8.
 */
public class AddContactActivity extends ActivityPresenter<AddSomethingActivityDelegate> {
    private List<AddItem> mList;
    private boolean mFromEdit;
    private ContactParams mContactParams;
    private String mId;
    private String mName;


    @Override
    protected Class<AddSomethingActivityDelegate> getDelegateClass() {
        return AddSomethingActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewDelegate.verifyRequired()){
                    if(mFromEdit){
                        Map<String, String> map = CompileUtil.compile(mContactParams, getNewContactParams());
                        Log.e("xxxxxxxxxxxxxx", "xxxxxxxxxxx " + map);
                        if(map.size() > 0) {
                            RetrofitRequest.getInstance().updateClue(mContactParams.getId(), map);
                        }
                    } else {
                        RetrofitRequest.getInstance().addContact(getNewContactParams());
                    }
                    finish();
                }
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mContactParams = (ContactParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTACT);
        mId = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID);
        mName = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME);
        mFromEdit = false;
        if (mContactParams != null) {
            mFromEdit = true;
            viewDelegate.setTitle(R.string.edit_clue);
        } else {
            viewDelegate.setTitle(R.string.add_clue);
        }
        viewDelegate.setTitle(R.string.add_contact);
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
        item.setTitleRes(R.string.name);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getName())) {
            item.setContent(mContactParams.getName());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_REQUIRED_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.related_customer);
        if (!TextUtils.isEmpty(mName)) {
            item.setContent(mName);
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_UNABLE);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.department);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getDepartment())) {
            item.setContent(mContactParams.getDepartment());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.post);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getPosition())) {
            item.setContent(mContactParams.getPosition());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.contact_information);
        item.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.phone);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getPhone())) {
            item.setContent(mContactParams.getPhone());
        }
        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.mobile);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getMobile())) {
            item.setContent(mContactParams.getMobile());
        }
        item.setEditTextType(InputType.TYPE_CLASS_PHONE);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.wechat);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getWechat())) {
            item.setContent(mContactParams.getWechat());
        }
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.qq);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getQq())) {
            item.setContent(mContactParams.getQq());
        }
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.wangwang);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getWangwang())) {
            item.setContent(mContactParams.getWangwang());
        }
        item.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        item = new AddItem();
        item.setTitleRes(R.string.email);
        if (mFromEdit && !TextUtils.isEmpty(mContactParams.getEmail())) {
            item.setContent(mContactParams.getEmail());
        }
        item.setEditTextType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        item.setItemType(AddSomethingRvAdapter.TYPE_ITEM_CAN_FILL);
        mList.add(item);
        viewDelegate.setData(mList);
    }



    public ContactParams getNewContactParams(){
        List<AddItem> list = viewDelegate.getData();
        ContactParams contactParams;
        if(mFromEdit) {
            try {
                contactParams = mContactParams.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            contactParams = new ContactParams();
        }
        for(AddItem item : list){
            if(item.getItemType() == AddSomethingRvAdapter.TYPE_LABEL){
                continue;
            }
            if(item.getTitleRes() == R.string.name){
                contactParams.setName(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.related_customer){
                contactParams.setCustomer(mId);
                continue;
            }
            if(item.getTitleRes() == R.string.department){
                contactParams.setDepartment(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.post){
                contactParams.setPosition(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.phone){
                contactParams.setPhone(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.mobile){
                contactParams.setMobile(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.qq){
                contactParams.setQq(item.getContent());
            }
            if(item.getTitleRes() == R.string.email){
                contactParams.setEmail(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.wechat){
                contactParams.setWechat(item.getContent());
                continue;
            }
            if(item.getTitleRes() == R.string.wangwang){
                contactParams.setWangwang(item.getContent());
                continue;
            }
        }
        return contactParams;
    }
}