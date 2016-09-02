package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-8-26.
 */
public class CustomerHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener{
    CustomerParams mCustomerParams;
//    CharSequence[] mStateArray;
//    CharSequence[] mStateArrayWeb;

    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.customer_home);
        viewDelegate.setFrom(Big4HomeActivityDelegate.FROM_CUSTOMER);
//        mStateArray = getResources().getStringArray(R.array.customer_status);
//        mStateArrayWeb = getResources().getStringArray(R.array.customer_status_web);
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mCustomerParams.getName());
//        ((TextView) viewDelegate.get(R.id.tv_follow_state)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mCustomerParams.getStatus())]);
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mCustomerParams);

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingMenuListener(R.menu.clue_spec, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        viewDelegate.setOnClickListener(this, R.id.rl_top);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_top) {
            Intent intent = new Intent(this, CustomerSpecActivity.class);
            intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<CustomerParams> list) {
        mCustomerParams = list.get(0);
        viewDelegate.setEntity(mCustomerParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
