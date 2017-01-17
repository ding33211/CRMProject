/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soubu.crmproject.base.mvp.presenter;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.IDelegate;
import com.soubu.crmproject.utils.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;


/**
 * Presenter层的实现基类
 * @param <T> View delegate class type
 */
@RuntimePermissions
public abstract class FragmentPresenter<T extends IDelegate> extends Fragment {
    public T viewDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDelegate.create(inflater, container, savedInstanceState);
        return viewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (viewDelegate.ifNeedEventBus()) {
            Log.e("xxxxxx", "fragment register");
            EventBus.getDefault().register(this);
        }
        viewDelegate.initWidget();
        initData();
        initView();
        bindEvenListener();
    }

    protected void initView() {

    }


    protected void initData() {

    }

    protected void bindEvenListener() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewDelegate.getOptionsMenuId() != 0) {
            inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(viewDelegate.ifNeedEventBus()){
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if(viewDelegate.ifNeedEventBus()){
//            EventBus.getDefault().unregister(this);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewDelegate.ifNeedEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();



    public void callSomeOne(String phone) {
        FragmentPresenterPermissionsDispatcher.loadWithCheck(this, phone);
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.CALL_PHONE})
    void load(String phone) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    //之前拒绝过这个请求,当再次请求这个权限的时候调起的方法
    //建议是对话框的方式,告知用户请求这个权限的原因
    //注意由于是在build中生成的类文件,因此每次对注释方法有有修改需要clean,rebuild.
    @OnShowRationale({Manifest.permission.CALL_PHONE})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(CrmApplication.getContext(), R.string.permission_explain_phone, request);
    }

    @OnPermissionDenied({Manifest.permission.CALL_PHONE})
    void onPermissionDenied() {
//        finish();
    }

    @OnNeverAskAgain({Manifest.permission.CALL_PHONE})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(getActivity(), R.string.permission_explain_phone, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        FragmentPresenterPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                default:
                    break;
            }
        }
    }

}
