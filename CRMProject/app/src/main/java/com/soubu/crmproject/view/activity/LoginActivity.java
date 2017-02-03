package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.LoginActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.utils.UserManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dingsigang on 16-9-9.
 */
public class LoginActivity extends ActivityPresenter<LoginActivityDelegate> implements View.OnClickListener {
    private boolean mDisplayPwd = false;

    @Override
    protected Class<LoginActivityDelegate> getDelegateClass() {
        return LoginActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_register, R.id.btn_login, R.id.iv_clear, R.id.iv_clear_user, R.id.iv_transfer_pwd, R.id.tv_forget_pwd);
        viewDelegate.get(R.id.et_user).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewDelegate.get(R.id.ll_user).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_selected));
                    ((ImageView) viewDelegate.get(R.id.iv_user)).setImageResource(R.drawable.login_user_selected);
                } else {
                    viewDelegate.get(R.id.ll_user).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_login_normal));
                    ((ImageView) viewDelegate.get(R.id.iv_user)).setImageResource(R.drawable.login_user_normal);
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

        ((EditText) viewDelegate.get(R.id.et_password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    viewDelegate.get(R.id.iv_clear).setVisibility(View.GONE);
                } else {
                    viewDelegate.get(R.id.iv_clear).setVisibility(View.VISIBLE);
                }
            }
        });
        viewDelegate.get(R.id.et_password).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewDelegate.get(R.id.iv_clear).setVisibility(View.GONE);
                } else {
                    if (!TextUtils.isEmpty(((EditText) v).getText().toString())) {
                        viewDelegate.get(R.id.iv_clear).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        ((EditText) viewDelegate.get(R.id.et_user)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    viewDelegate.get(R.id.iv_clear_user).setVisibility(View.GONE);
                } else {
                    viewDelegate.get(R.id.iv_clear_user).setVisibility(View.VISIBLE);
                }
            }
        });
        viewDelegate.get(R.id.et_user).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    viewDelegate.get(R.id.iv_clear_user).setVisibility(View.GONE);
                } else {
                    if (!TextUtils.isEmpty(((EditText) v).getText().toString())) {
                        viewDelegate.get(R.id.iv_clear_user).setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                UserParams params = new UserParams();
                if (viewDelegate.verify(params)) {
                    RetrofitRequest.getInstance().login(params);
                    mEventBusJustForThis = true;
                }
                break;
            case R.id.iv_clear:
                ((EditText) viewDelegate.get(R.id.et_password)).setText("");
                break;
            case R.id.iv_transfer_pwd:
                if (!mDisplayPwd) {
                    ((EditText) viewDelegate.get(R.id.et_password)).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.show_pwd);
                    mDisplayPwd = true;
                } else {
                    ((EditText) viewDelegate.get(R.id.et_password)).setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ((ImageView) viewDelegate.get(R.id.iv_transfer_pwd)).setImageResource(R.drawable.hide_pwd);
                    mDisplayPwd = false;
                }
                break;
            case R.id.iv_clear_user:
                ((EditText) viewDelegate.get(R.id.et_user)).setText("");
                break;

            case R.id.tv_forget_pwd:
                ShowWidgetUtil.showShort(R.string.forget_pwd_alert);
                break;

        }
    }

    //是否需要退出
    private boolean mNeedQuit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mNeedQuit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mNeedQuit) {
                mNeedQuit = true;
                ShowWidgetUtil.showShort(R.string.click_again_to_quit);
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                finish();
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(UserParams[] userParams) {
        if (!mEventBusJustForThis) {
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (userParams != null && userParams.length > 0) {
            if (!TextUtils.isEmpty(userParams[0].getToken()) && !TextUtils.isEmpty(userParams[0].getId())) {
//                User user = userParams[0].copyToUser();
//                UserDao userDao = DBHelper.getInstance(getApplicationContext()).getUserDao();
//                List<User> list = userDao.queryBuilder().where(UserDao.Properties.User_id.eq(user.getId())).list();
//                if(list != null && list.size() > 0){
//                    list.get(0)
//                }
//                DBHelper.getInstance(getApplicationContext()).getUserDao().insert(user);
                CrmApplication.getContext().setToken(userParams[0].getToken());
                CrmApplication.getContext().setUid(userParams[0].getId());
                CrmApplication.getContext().setName(userParams[0].getUsername());
                UserManager.saveUserInfo(userParams[0]);
                if (getIntent().getIntExtra(Contants.EXTRA_FROM, -1) != Contants.FROM_SPLASH) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    setResult(RESULT_OK, null);
                }
                finish();
            } else {
                ShowWidgetUtil.showShort(R.string.login_error_message);
            }
        }
    }

}
