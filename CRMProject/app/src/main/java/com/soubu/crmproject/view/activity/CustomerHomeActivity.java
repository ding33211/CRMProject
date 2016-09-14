package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
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
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-26.
 */
public class CustomerHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
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
        ((TextView) viewDelegate.get(R.id.tv_sub_left)).setText(SearchUtil.searchCustomerPropertyArray(this)[SearchUtil.searchInArray(SearchUtil.searchCustomerPropertyWebArray(this), mCustomerParams.getProperty())]);
        ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(SearchUtil.searchCustomerTypeArray(this)[SearchUtil.searchInArray(SearchUtil.searchCustomerTypeWebArray(this), mCustomerParams.getType())]);
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
        viewDelegate.setOnClickListener(this, R.id.rl_content, R.id.ll_go_right, R.id.ll_left, R.id.ll_right);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_content:
                Intent intent = new Intent(this, CustomerSpecActivity.class);
                intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
                startActivity(intent);
                break;
            case R.id.ll_go_right:
                Intent intent1 = new Intent(this, ContactActivity.class);
                intent1.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
                intent1.putExtra(Contants.EXTRA_CUSTOMER_NAME, mCustomerParams.getName());
                startActivity(intent1);
                break;
            case R.id.ll_left:
                Intent intent2 = new Intent(this, BusinessOpportunityActivity.class);
                intent2.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
                startActivity(intent2);
                break;
            case R.id.ll_right:
                Intent intent3 = new Intent(this, ContractActivity.class);
                intent3.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
                startActivity(intent3);
                break;
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
        List<FollowParams> records = new ArrayList<>();
        List<FollowParams> plans = new ArrayList<>();
        for(FollowParams param : list){
            if(TextUtils.equals(param.getType(), Contants.FOLLOW_TYPE_PLAN)){
                plans.add(param);
            } else {
                records.add(param);
            }
        }
        viewDelegate.setViewPagerData(0, records);
        viewDelegate.setViewPagerData(1, plans);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitRequest.getInstance().getCustomerFollow(mCustomerParams.getId(), null, null, null, null, null);
    }
}
