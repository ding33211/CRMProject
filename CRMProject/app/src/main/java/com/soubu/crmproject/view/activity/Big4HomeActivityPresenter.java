package com.soubu.crmproject.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-9-19.
 */
public abstract class Big4HomeActivityPresenter<T extends AppDelegate> extends ActivityPresenter<T> implements View.OnClickListener {
    List<DialogItem> mPhoneList;
    List<DialogItem> mWechatList;
    List<DialogItem> mEmailList;
    List<DialogItem> mQqList;
    String mLocation;
    int mIndex = -1;

    class DialogItem {
        String name;
        String value;
        String id;
    }

    @Override
    protected void initView() {
        super.initView();
        mPhoneList = new ArrayList<>();
        mWechatList = new ArrayList<>();
        mEmailList = new ArrayList<>();
        mQqList = new ArrayList<>();
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(mListener, R.id.iv_sns1, R.id.iv_sns2, R.id.iv_sns3, R.id.iv_sns4, R.id.iv_sns5);
    }


    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_sns1:
                    if (mPhoneList.size() > 1) {
                        final String[] items = new String[mPhoneList.size()];
                        for (int i = 0; i < mPhoneList.size(); i++) {
                            items[i] = (TextUtils.isEmpty(mPhoneList.get(i).name) ? "" : mPhoneList.get(i).name + " :  ") + mPhoneList.get(i).value;
                        }
                        AlertDialog dialog = new AlertDialog.Builder(Big4HomeActivityPresenter.this).setTitle(R.string.please_choose_phone)
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
                                        if (!TextUtils.isEmpty(mPhoneList.get(0).id)) {
                                            RetrofitRequest.getInstance().touchContact(mPhoneList.get(mIndex).id);
                                        }
                                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(mIndex).value));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).setCancelable(false).create();
                        dialog.show();
                    } else if (mPhoneList.size() == 1) {
                        if (!TextUtils.isEmpty(mPhoneList.get(0).id)) {
                            RetrofitRequest.getInstance().touchContact(mPhoneList.get(0).id);
                        }
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(0).value));
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_sns2:
                    if (mWechatList.size() > 0) {

                    }
                    break;
                case R.id.iv_sns3:
                    if (mEmailList.size() > 0) {

                    }
                    break;
                case R.id.iv_sns4:
                    break;
                case R.id.iv_sns5:
                    if (mQqList.size() > 0) {

                    }
                    break;
            }
        }
    };

    void initConnectionView(List<ContactParams> list) {
        mPhoneList.clear();
        mEmailList.clear();
        mQqList.clear();
        mWechatList.clear();
        for (ContactParams contact : list){
            if(!TextUtils.isEmpty(contact.getMobile())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getMobile();
                item.id = contact.getId();
                mPhoneList.add(item);
            }
            if(!TextUtils.isEmpty(contact.getPhone())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getPhone();
                item.id = contact.getId();
                mPhoneList.add(item);
            }
            if(!TextUtils.isEmpty(contact.getWechat())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getMobile();
                item.id = contact.getId();
                mWechatList.add(item);
            }
            if(!TextUtils.isEmpty(contact.getEmail())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getMobile();
                item.id = contact.getId();
                mEmailList.add(item);
            }
            //qq服务器字段存的是int类型,每次拉下来即使没有也是0
            if(!TextUtils.isEmpty(contact.getQq()) && !TextUtils.equals(contact.getQq(), "0")){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getMobile();
                item.id = contact.getId();
                mQqList.add(item);
            }
        }
        initConnectionView();
    }

    void initConnectionView() {
        if (mPhoneList.size() == 0) {
            viewDelegate.get(R.id.iv_sns1).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns1).setAlpha(1);
        }
        if (mWechatList.size() == 0) {
            viewDelegate.get(R.id.iv_sns2).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns2).setAlpha(1);
        }
        if (mEmailList.size() == 0) {
            viewDelegate.get(R.id.iv_sns3).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns3).setAlpha(1);
        }
        if(TextUtils.isEmpty(mLocation)){
            viewDelegate.get(R.id.iv_sns4).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns4).setAlpha(1);
        }
        if (mQqList.size() == 0) {
            viewDelegate.get(R.id.iv_sns5).setAlpha(0.3f);
        } else {
            viewDelegate.get(R.id.iv_sns5).setAlpha(1);
        }
    }
}
