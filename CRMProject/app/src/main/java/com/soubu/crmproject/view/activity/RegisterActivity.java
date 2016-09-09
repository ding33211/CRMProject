package com.soubu.crmproject.view.activity;

import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.RegisterActivityDelegate;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.server.RetrofitRequest;
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
            ShowWidgetUtil.showLong(R.string.register_success_message);
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
