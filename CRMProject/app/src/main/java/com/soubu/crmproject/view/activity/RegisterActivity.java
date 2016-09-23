package com.soubu.crmproject.view.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
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
public class RegisterActivity extends ActivityPresenter<RegisterActivityDelegate> implements View.OnClickListener {
    boolean mDisplayPwd = false;
    boolean mDisplayPwdAgain = false;

    @Override
    protected Class<RegisterActivityDelegate> getDelegateClass() {
        return RegisterActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_register, R.id.iv_clear_email, R.id.iv_clear_user, R.id.iv_clear_password, R.id.iv_clear_password_again,
                R.id.iv_transfer_pwd, R.id.iv_transfer_pwd_again);
        viewDelegate.get(R.id.et_email).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewDelegate.get(R.id.ll_email).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView) viewDelegate.get(R.id.iv_email)).setImageResource(R.drawable.register_email_selected);
                } else {
                    viewDelegate.get(R.id.ll_email).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView) viewDelegate.get(R.id.iv_email)).setImageResource(R.drawable.register_email_normal);
                }
            }
        });

        viewDelegate.get(R.id.et_user_name).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewDelegate.get(R.id.ll_user_name).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView) viewDelegate.get(R.id.iv_user_name)).setImageResource(R.drawable.login_user_selected);
                } else {
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView) viewDelegate.get(R.id.iv_user_name)).setImageResource(R.drawable.login_user_normal);
                }
            }
        });

        viewDelegate.get(R.id.et_password).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView) viewDelegate.get(R.id.iv_password)).setImageResource(R.drawable.login_lock_selected);
                } else {
                    viewDelegate.get(R.id.ll_password).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView) viewDelegate.get(R.id.iv_password)).setImageResource(R.drawable.login_lock_normal);
                }
            }
        });

        viewDelegate.get(R.id.et_password_again).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewDelegate.get(R.id.ll_password_again).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView) viewDelegate.get(R.id.iv_password_again)).setImageResource(R.drawable.login_lock_selected);
                } else {
                    viewDelegate.get(R.id.ll_password_again).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView) viewDelegate.get(R.id.iv_password_again)).setImageResource(R.drawable.login_lock_normal);
                }
            }
        });
        initEditText(R.id.et_password, R.id.iv_clear_password);
        initEditText(R.id.et_user_name, R.id.iv_clear_user);
        initEditText(R.id.et_password_again, R.id.iv_clear_password_again);
        initEditText(R.id.et_email, R.id.iv_clear_email);
    }

    private void initEditText(int editTextId, final int clearImgId){
        ((EditText)viewDelegate.get(editTextId)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    viewDelegate.get(clearImgId).setVisibility(View.GONE);
                } else {
                    viewDelegate.get(clearImgId).setVisibility(View.VISIBLE);
                }
            }
        });
        viewDelegate.get(editTextId).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewDelegate.get(clearImgId).setVisibility(View.GONE);
                } else {
                    if(!TextUtils.isEmpty(((EditText)v).getText().toString())){
                        viewDelegate.get(clearImgId).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_register:
                UserParams params = new UserParams();
                if (viewDelegate.verify(params)) {
                    RetrofitRequest.getInstance().register(params);
                }
                break;
            case R.id.iv_clear_email:
                ((EditText) viewDelegate.get(R.id.et_email)).setText("");
                break;
            case R.id.iv_clear_user:
                ((EditText) viewDelegate.get(R.id.et_user_name)).setText("");
                break;
            case R.id.iv_clear_password:
                ((EditText) viewDelegate.get(R.id.et_password)).setText("");
                break;
            case R.id.iv_clear_password_again:
                ((EditText) viewDelegate.get(R.id.et_password_again)).setText("");
                break;
            case R.id.iv_transfer_pwd:
                if(!mDisplayPwd){
                    ((EditText)viewDelegate.get(R.id.et_password)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView)viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.show_pwd);
                    mDisplayPwd = true;
                } else {
                    ((EditText)viewDelegate.get(R.id.et_password)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView)viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.hide_pwd);
                    mDisplayPwd = false;
                }
                break;
            case R.id.iv_transfer_pwd_again:
                if(!mDisplayPwdAgain){
                    ((EditText)viewDelegate.get(R.id.et_password_again)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView)viewDelegate.get(R.id.iv_transfer_pwd_again)).setImageResource(R.drawable.show_pwd);
                    mDisplayPwdAgain = true;
                } else {
                    ((EditText)viewDelegate.get(R.id.et_password_again)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView)viewDelegate.get(R.id.iv_transfer_pwd_again)).setImageResource(R.drawable.hide_pwd);
                    mDisplayPwdAgain = false;
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UserParams[] userParams) {
        if (userParams != null && userParams.length > 0) {
            if (TextUtils.isEmpty(userParams[0].getId())) {
                ShowWidgetUtil.showShort(R.string.register_error_message);
            } else {
                ShowWidgetUtil.showLong(R.string.register_success_message);
                finish();
            }

        }
    }

}
