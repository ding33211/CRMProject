package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class BusinessOpportunityHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener{
    BusinessOpportunityParams mBusinessOpportunityParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateArrayWeb;

    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mBusinessOpportunityParams);
        Log.e("xxx","xxxx");
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.rl_content);
        viewDelegate.setSettingMenuListener(R.menu.clue_home, new PopupMenu.OnMenuItemClickListener() {
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
            Intent intent = new Intent(this, BusinessOpportunitySpecActivity.class);
            intent.putExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY, mBusinessOpportunityParams);
            startActivity(intent);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.business_opportunity);
        viewDelegate.setFrom(Big4HomeActivityDelegate.FROM_BUSINESS_OPPORTUNITY);
        mStateArray = getResources().getStringArray(R.array.business_opportunity_status);
        mStateArrayWeb = getResources().getStringArray(R.array.business_opportunity_status_web);
        mBusinessOpportunityParams = (BusinessOpportunityParams) getIntent().getSerializableExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mBusinessOpportunityParams.getTitle());
        ((TextView) viewDelegate.get(R.id.tv_company_name)).setText(mBusinessOpportunityParams.getCustomer());
        ((TextView) viewDelegate.get(R.id.tv_follow_state)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mBusinessOpportunityParams.getStatus())]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(List<BusinessOpportunityParams> list) {
        mBusinessOpportunityParams = list.get(0);
        viewDelegate.setEntity(mBusinessOpportunityParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }
}
