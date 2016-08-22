package com.soubu.crmproject.view.activity;

import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.ClueActivityDelegate;

/**
 * Created by dingsigang on 16-8-22.
 */
public class ClueSpecActivity extends ActivityPresenter<ClueActivityDelegate> {
    @Override
    protected Class<ClueActivityDelegate> getDelegateClass() {
        return ClueActivityDelegate.class;
    }
}
