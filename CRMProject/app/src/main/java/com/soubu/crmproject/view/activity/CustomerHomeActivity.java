package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
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
        viewDelegate.setFrom(Contants.FROM_CUSTOMER);
//        mStateArray = getResources().getStringArray(R.array.customer_status);
//        mStateArrayWeb = getResources().getStringArray(R.array.customer_status_web);
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mCustomerParams.getName());
        ((TextView) viewDelegate.get(R.id.tv_subtitle)).setText(mCustomerParams.getProperty());
        ((TextView) viewDelegate.get(R.id.tv_left_number)).setText(mCustomerParams.getDealsCount());
        ((TextView) viewDelegate.get(R.id.tv_right_number)).setText(mCustomerParams.getContractsCount());
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mCustomerParams);

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setSettingMenuListener(R.menu.customer_home, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        viewDelegate.setOnClickListener(this, R.id.rl_content);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_content) {
            Intent intent = new Intent(this, CustomerSpecActivity.class);
            intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        List<CustomerParams> list = Arrays.asList(params);
        mCustomerParams = list.get(0);
        viewDelegate.setEntity(mCustomerParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFollow(FollowParams[] params) {
        List<FollowParams> list = Arrays.asList(params);
        viewDelegate.setViewPagerData(1, list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitRequest.getInstance().getCustomerFollow(mCustomerParams.getId(), null, null, null, null, null);
    }
}
