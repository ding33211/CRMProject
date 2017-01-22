package com.soubu.crmproject.delegate;

import android.text.TextUtils;
import android.widget.EditText;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.utils.RegularUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

/**
 * Created by dingsigang on 16-9-9.
 */
public class RegisterActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.register);
    }

    public boolean verify(UserParams params){
        String email = ((EditText)get(R.id.et_email)).getText().toString();
        if(TextUtils.isEmpty(email)){
            ShowWidgetUtil.showLong(R.string.please_input_email);
            return false;
        }
        if(!RegularUtil.isEmail(email)){
            ShowWidgetUtil.showLong(R.string.wrong_email);
            return false;
        }
        params.setEmail(email);
        String nickName = ((EditText)get(R.id.et_user_name)).getText().toString();
        if(TextUtils.isEmpty(nickName)){
            ShowWidgetUtil.showLong(R.string.please_input_user_name);
            return false;
        }
        params.setUsername(nickName);
        String pwd = ((EditText)get(R.id.et_password)).getText().toString();
        if(TextUtils.isEmpty(pwd)){
            ShowWidgetUtil.showLong(R.string.please_input_password);
            return false;
        }
        String pwdAgain = ((EditText)get(R.id.et_password_again)).getText().toString();
        if(TextUtils.isEmpty(pwdAgain)){
            ShowWidgetUtil.showLong(R.string.please_input_password_again);
            return false;
        }
        if(!TextUtils.equals(pwd, pwdAgain)){
            ShowWidgetUtil.showLong(R.string.two_password_not_same);
            return false;
        }
        if(pwd.length() < 8){
            ShowWidgetUtil.showLong(R.string.password_regular_wrong);
            return false;
        }
        params.setPwd(pwd);
        params.setConfirmPwd(pwd);
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }



}
