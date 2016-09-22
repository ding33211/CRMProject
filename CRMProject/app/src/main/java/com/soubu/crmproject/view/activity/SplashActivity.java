package com.soubu.crmproject.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.greendao.Staff;
import com.soubu.crmproject.base.greendao.StaffDao;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SplashActivityDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.PermissionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 欢迎页
 */
//标注这个类需要使用到6.0动态权限请求
@RuntimePermissions
public class SplashActivity extends ActivityPresenter<SplashActivityDelegate> {
    private StaffDao mStaffDao;
    private final int REQUEST_LOGIN = 1002;


    @Override
    protected Class<SplashActivityDelegate> getDelegateClass() {
        return SplashActivityDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化个推
        PushManager.getInstance().initialize(this.getApplicationContext());
        DBHelper helper = DBHelper.getInstance(getApplicationContext());
        mStaffDao = helper.getStaffDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                SplashActivityPermissionsDispatcher.loadWithCheck(SplashActivity.this);
            }
        }).start();
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    void load() {
        if(TextUtils.isEmpty(CrmApplication.getContext().getToken())){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        } else {
//            if(mStaffDao.count() == 0){
//                RetrofitRequest.getInstance().getStaffList();
//                mEventBusJustForThis = true;
//            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).start();

//            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UserParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        Log.e("mStaffDao.count()", mStaffDao.count() + "");
        if(params != null && params.length > 0){
            //如果有token,实际拿到的是登录成功的回调
            if(!TextUtils.isEmpty(params[0].getToken())){
                return;
            }
            Staff staff;
            for(UserParams user : params){
                staff = new Staff();
                staff.setNickname(user.getNickName());
                staff.setUsername(user.getUserName());
                staff.setActivated(user.getActivated());
                staff.setActivatedAt(user.getActivatedAt());
                staff.setCreatedAt(user.getCreatedAt());
                staff.setDepartment(user.getDepartment());
                staff.setEmail(user.getEmail());
                staff.setEmployeeNumber(user.getEmployeeNumber());
                staff.setStaff_id(user.getId());
                staff.setMobile(user.getMobile());
                Log.e("xxxxxxxxx", new Gson().toJson(staff));
                mStaffDao.insert(staff);
            }
        }
        Log.e("xx mStaffDao.count()", mStaffDao.count() + "");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
    }

    //之前拒绝过这个请求,当再次请求这个权限的时候调起的方法
    //建议是对话框的方式,告知用户请求这个权限的原因
    //注意由于是在build中生成的类文件,因此每次对注释方法有有修改需要clean,rebuild.
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(getApplicationContext(), R.string.permission_explain_storage, request);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    void onPermissionDenied() {
        finish();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_storage, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                case REQUEST_LOGIN:
                    load();
                default:
                    break;
            }
        }
    }
}
