package com.soubu.crmproject.view.activity;

import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddClueActivityDelegate;

/**
 * Created by dingsigang on 16-8-19.
 */
public class AddClueActivity extends ActivityPresenter<AddClueActivityDelegate> {
    @Override
    protected Class<AddClueActivityDelegate> getDelegateClass() {
        return AddClueActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingText(R.string.save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
