package com.soubu.crmproject.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.RegisterActivityDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-9-9.
 */
public class RegisterActivity extends ActivityPresenter<RegisterActivityDelegate> implements View.OnClickListener{

    @Override
    protected Class<RegisterActivityDelegate> getDelegateClass() {
        return RegisterActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_register);
        viewDelegate.get(R.id.et_email).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewDelegate.get(R.id.ll_email).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView)viewDelegate.get(R.id.iv_email)).setImageResource(R.drawable.register_email_selected);
                } else {
                    viewDelegate.get(R.id.ll_email).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView)viewDelegate.get(R.id.iv_email)).setImageResource(R.drawable.register_email_normal);
                }
            }
        });

        viewDelegate.get(R.id.et_user_name).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewDelegate.get(R.id.ll_user_name).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView)viewDelegate.get(R.id.iv_user_name)).setImageResource(R.drawable.login_user_selected);
                } else {
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView)viewDelegate.get(R.id.iv_user_name)).setImageResource(R.drawable.login_user_normal);
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

        viewDelegate.get(R.id.et_password_again).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    viewDelegate.get(R.id.ll_password_again).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView)viewDelegate.get(R.id.iv_password_again)).setImageResource(R.drawable.login_lock_selected);
                } else {
                    viewDelegate.get(R.id.ll_password_again).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView)viewDelegate.get(R.id.iv_password_again)).setImageResource(R.drawable.login_lock_normal);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_register:
                UserParams params = new UserParams();
                if(viewDelegate.verify(params)){
                    RetrofitRequest.getInstance().register(params);
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UserParams[] userParams) {
        if(userParams != null && userParams.length > 0){
            if(TextUtils.isEmpty(userParams[0].getId())){
                ShowWidgetUtil.showShort(R.string.register_error_message);
            } else {
                ShowWidgetUtil.showLong(R.string.register_success_message);
                finish();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
    }
}
