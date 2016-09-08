package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AlreadyBackSalesActivityDelegate;
import com.soubu.crmproject.model.Contants;

/**
 * Created by dingsigang on 16-9-8.
 */
public class AlreadyBackSalesActivity extends ActivityPresenter<AlreadyBackSalesActivityDelegate> {
    @Override
    protected Class<AlreadyBackSalesActivityDelegate> getDelegateClass() {
        return AlreadyBackSalesActivityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlreadyBackSalesActivity.this, AddBackSalesActivity.class);
                intent.putExtra(Contants.EXTRA_CONTRACT_ID, getIntent().getStringExtra(Contants.EXTRA_CONTRACT_ID));
                startActivity(intent);
            }
        });
    }
}
