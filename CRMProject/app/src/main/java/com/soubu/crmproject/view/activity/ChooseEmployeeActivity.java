package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ChooseEmployeeActivityDelegate;

/**
 * Created by dingsigang on 16-8-25.
 */
public class ChooseEmployeeActivity extends ActivityPresenter<ChooseEmployeeActivityDelegate>{

    @Override
    protected Class<ChooseEmployeeActivityDelegate> getDelegateClass() {
        return ChooseEmployeeActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.choose_transfer);
    }
}
