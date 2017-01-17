package com.soubu.crmproject.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddSomethingRvAdapter;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SpecActivityDelegate;
import com.soubu.crmproject.model.AddItem;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-9.
 */
public class ContactSpecActivity extends ActivityPresenter<SpecActivityDelegate> implements View.OnClickListener{
    List<AddItem> mList;
    boolean hasTop;
    ContactParams mContactParams;
    List<String> mPhoneList;
    int mIndex = -1;

    @Override
    protected Class<SpecActivityDelegate> getDelegateClass() {
        return SpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setContactVisible();
        mContactParams = (ContactParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTACT);
        mPhoneList = new ArrayList<>();
        initConnectionMethod(mContactParams);
        initContactParams(mContactParams);
    }

    private void initConnectionMethod(ContactParams params){
        if (TextUtils.isEmpty(params.getPhone()) && TextUtils.isEmpty(params.getMobile())) {
            viewDelegate.get(R.id.iv_sns1).setAlpha(0.3f);
        } else {
            if(!TextUtils.isEmpty(params.getMobile())){
                mPhoneList.add(params.getMobile());
            }
            if(!TextUtils.isEmpty(params.getPhone())){
                mPhoneList.add(params.getPhone());
            }
            viewDelegate.get(R.id.iv_sns1).setAlpha(1);
        }
        if (TextUtils.isEmpty(params.getWechat())) {
            viewDelegate.get(R.id.iv_sns2).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns2).setAlpha(1);
        }
        if (TextUtils.isEmpty(params.getEmail())) {
            viewDelegate.get(R.id.iv_sns3).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns3).setAlpha(1);
        }
        if (TextUtils.isEmpty(params.getPhone()) && TextUtils.isEmpty(params.getMobile())) {
            viewDelegate.get(R.id.iv_sns4).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns4).setAlpha(1);
        }
        if (TextUtils.isEmpty(params.getQq())) {
            viewDelegate.get(R.id.iv_sns5).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns5).setAlpha(1);
        }
    }

    private void initContactParams(ContactParams contactParams) {
        mList = new ArrayList<>();
        AddItem addItem = new AddItem();
        addItem.setTitleRes(R.string.essential_information);
        addItem.setItemType(AddSomethingRvAdapter.TYPE_LABEL);
        mList.add(addItem);
        initItem(contactParams.getName(), R.string.name, true);
        if(contactParams.getCustomer() != null){
            initItem(contactParams.getCustomer().getName(), R.string.related_customer, hasTop ? false : true);
        }
        initItem(contactParams.getPosition(), R.string.post, hasTop ? false : true);
        initItem(contactParams.getDepartment(), R.string.department, hasTop ? false : true);
        if(!hasTop){
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContactParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<ContactParams> list = Arrays.asList(params);
        mContactParams = list.get(0);
        initContactParams(mContactParams);
        ContactDao contactDao =  DBHelper.getInstance(getApplicationContext()).getContactDao();
        List<Contact> contactList = contactDao.queryBuilder().where(ContactDao.Properties.Contact_id.eq(mContactParams.getId())).list();
        if(contactList != null && contactList.size() > 0){
            Contact contact = mContactParams.copyToContact();
            contact.setId(contactList.get(0).getId());
            contactDao.update(contact);
        } else {
            Contact contact = mContactParams.copyToContact();
            contactDao.insert(contact);
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventBusJustForThis = true;
                Intent intent = new Intent(ContactSpecActivity.this, AddContactActivity.class);
                intent.putExtra(Contants.EXTRA_CONTACT, mContactParams);
                intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                startActivity(intent);
            }
        });
        viewDelegate.setOnClickListener(this, R.id.iv_sns1, R.id.iv_sns2, R.id.iv_sns3, R.id.iv_sns4, R.id.iv_sns5);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sns1:
                if (mPhoneList.size() > 1) {
                    final String[] items = new String[mPhoneList.size()];
                    for (int i = 0; i < mPhoneList.size(); i++) {
                        items[i] = mPhoneList.get(i);
                    }
                    AlertDialog dialog = new AlertDialog.Builder(ContactSpecActivity.this).setTitle(R.string.please_choose_phone)
                            .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mIndex = which;
                                }
                            }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(mIndex == -1){
                                        ShowWidgetUtil.showShort(R.string.please_choose_phone);
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(mContactParams.getId())) {
                                        RetrofitRequest.getInstance().touchContact(mContactParams.getId());
                                    }
                                    callSomeOne(mPhoneList.get(mIndex));
//                                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(mIndex)));
//                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setCancelable(false).create();
                    dialog.show();
                } else if (mPhoneList.size() == 1) {
                    if (!TextUtils.isEmpty(mContactParams.getId())) {
                        RetrofitRequest.getInstance().touchContact(mContactParams.getId());
                    }
                    callSomeOne(mPhoneList.get(0));
//                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(0)));
//                    startActivity(intent);
                }
                break;
            case R.id.iv_sns2:
                break;
            case R.id.iv_sns3:
                break;
            case R.id.iv_sns4:
                break;
            case R.id.iv_sns5:
                break;
        }
    }
}
