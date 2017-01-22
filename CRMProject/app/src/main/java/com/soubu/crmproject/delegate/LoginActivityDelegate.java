package com.soubu.crmproject.delegate;

import android.text.TextUtils;
import android.widget.EditText;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.utils.ShowWidgetUtil;

/**
 * Created by dingsigang on 16-9-9.
 */
public class LoginActivityDelegate extends AppDelegate{
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }

    public Boolean verify(UserParams params){
        String userName = ((EditText)get(R.id.et_user)).getText().toString();
        if(TextUtils.isEmpty(userName)){
            ShowWidgetUtil.showLong(R.string.please_input_user_name);
            return false;
        }
        params.setLoginname(userName);
        String password = ((EditText)get(R.id.et_password)).getText().toString();
        if(TextUtils.isEmpty(password)){
            ShowWidgetUtil.showLong(R.string.please_input_password);
            return false;
        }
        params.setPwd(password);
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
