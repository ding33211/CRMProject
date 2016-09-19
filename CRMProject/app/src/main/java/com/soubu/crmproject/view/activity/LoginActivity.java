package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.soubu.crmproject.MyApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.common.ApiConfig;
import com.soubu.crmproject.delegate.LoginActivityDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-9-9.
 */
public class LoginActivity extends ActivityPresenter<LoginActivityDelegate> implements View.OnClickListener{

    @Override
    protected Class<LoginActivityDelegate> getDelegateClass() {
        return LoginActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_register, R.id.btn_login);
        viewDelegate.get(R.id.et_user).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewDelegate.get(R.id.ll_user).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView)viewDelegate.get(R.id.iv_user)).setImageResource(R.drawable.login_user_selected);
                } else {
                    viewDelegate.get(R.id.ll_user).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView)viewDelegate.get(R.id.iv_user)).setImageResource(R.drawable.login_user_normal);
                }
            }
        });

        viewDelegate.get(R.id.et_password).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView)viewDelegate.get(R.id.iv_password)).setImageResource(R.drawable.login_lock_selected);
                } else {
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView)viewDelegate.get(R.id.iv_password)).setImageResource(R.drawable.login_lock_normal);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                UserParams params = new UserParams();
                if(viewDelegate.verify(params)){
                    RetrofitRequest.getInstance().login(params);
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UserParams[] userParams) {
        if(userParams != null && userParams.length > 0){
            if(!TextUtils.isEmpty(userParams[0].getToken()) && !TextUtils.isEmpty(userParams[0].getId())){
                MyApplication.getContext().setToken(userParams[0].getToken());
                MyApplication.getContext().setUid(userParams[0].getId());
                setResult(RESULT_OK, null);
                finish();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
    }


}
