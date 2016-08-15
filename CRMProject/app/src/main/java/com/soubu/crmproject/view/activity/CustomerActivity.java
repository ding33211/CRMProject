package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.CustomerActivityDelegate;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivity extends ActivityPresenter<CustomerActivityDelegate> {
    @Override
    protected Class<CustomerActivityDelegate> getDelegateClass() {
        return CustomerActivityDelegate.class;
    }
}
