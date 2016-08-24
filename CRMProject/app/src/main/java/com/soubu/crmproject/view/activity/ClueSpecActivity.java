package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ClueSpecActivityDelegate;

/**
 * Created by dingsigang on 16-8-24.
 */
public class ClueSpecActivity extends ActivityPresenter<ClueSpecActivityDelegate> {
    @Override
    protected Class<ClueSpecActivityDelegate> getDelegateClass() {
        return ClueSpecActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
