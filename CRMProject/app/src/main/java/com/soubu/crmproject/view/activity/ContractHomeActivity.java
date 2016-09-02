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
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowTest;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class ContractHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    ContractParams mContractParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateArrayWeb;

    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }


    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mContractParams);

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.rl_top);
        viewDelegate.setSettingMenuListener(R.menu.clue_spec, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rl_top) {
            Intent intent = new Intent(this, ContractSpecActivity.class);
            intent.putExtra(Contants.EXTRA_CONTRACT, mContractParams);
            startActivity(intent);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.contract_home);
        viewDelegate.setFrom(Big4HomeActivityDelegate.FROM_CONTRACT);
        mStateArray = getResources().getStringArray(R.array.contract_state);
        mStateArrayWeb = getResources().getStringArray(R.array.contract_state_web);
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mContractParams.getTitle());
        ((TextView) viewDelegate.get(R.id.tv_company_name)).setText(mContractParams.getCustomer());
        ((TextView) viewDelegate.get(R.id.tv_follow_state)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mContractParams.getStatus())]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<ContractParams> list) {
        mContractParams = list.get(0);
        viewDelegate.setEntity(mContractParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
