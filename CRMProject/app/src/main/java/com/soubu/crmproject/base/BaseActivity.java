package com.soubu.crmproject.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.soubu.crmproject.R;
import com.soubu.crmproject.utils.PermissionUtil;
import com.umeng.analytics.MobclickAgent;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 基类activity,放公有方法
 */
@RuntimePermissions
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //友盟埋点初始化
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void callSomeOne(String phone) {
        BaseActivityPermissionsDispatcher.loadWithCheck(this, phone);
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
        PermissionUtil.showPermissionExplainDialog(getApplicationContext(), R.string.permission_explain_phone, request);
    }

    @OnPermissionDenied({Manifest.permission.CALL_PHONE})
    void onPermissionDenied() {
//        finish();
    }

    @OnNeverAskAgain({Manifest.permission.CALL_PHONE})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_phone, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
