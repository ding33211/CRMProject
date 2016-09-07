package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.BackSalesPlanActivityDelegate;

/**
 * Created by dingsigang on 16-9-7.
 */
public class BackSalesPlanActivity extends ActivityPresenter<BackSalesPlanActivityDelegate> {

    @Override
    protected Class<BackSalesPlanActivityDelegate> getDelegateClass() {
        return BackSalesPlanActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BackSalesPlanActivity.this, AddBackSalesPlanActivity.class);
                startActivity(intent);
            }
        });
    }
}
