package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class ContractHomeActivity extends ActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    ContractParams mContractParams;

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
        viewDelegate.setOnClickListener(this, R.id.rl_content, R.id.ll_left, R.id.ll_right);
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
        switch (id) {
            case R.id.rl_content:
                Intent intent = new Intent(this, ContractSpecActivity.class);
                intent.putExtra(Contants.EXTRA_CONTRACT, mContractParams);
                startActivity(intent);
                break;
            case R.id.ll_left:
                Intent intent1 = new Intent(this, BackSalesPlanActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_right:
                Intent intent2 = new Intent(this, AlreadyBackSalesActivity.class);
                intent2.putExtra(Contants.EXTRA_CONTRACT_ID, mContractParams.getId());
                startActivity(intent2);
                break;
        }

    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.contract_home);
        viewDelegate.setFrom(Contants.FROM_CONTRACT);
        CharSequence[] stateArray = getResources().getStringArray(R.array.contract_state);
        CharSequence[] stateArrayWeb = getResources().getStringArray(R.array.contract_state_web);
        CharSequence[] reviewStateWebArray = getResources().getStringArray(R.array.contract_review_state_web);
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mContractParams.getTitle());
        ((TextView) viewDelegate.get(R.id.tv_subtitle)).setText(mContractParams.getCustomer());
        ((TextView) viewDelegate.get(R.id.tv_follow_state)).setText(stateArray[SearchUtil.searchInArray(stateArrayWeb, mContractParams.getStatus())]);
        ((TextView) viewDelegate.get(R.id.tv_contract_price)).setText(mContractParams.getAmountPrice());
        if (TextUtils.equals(mContractParams.getReviewStatus(), reviewStateWebArray[0])) {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state)).setImageResource(R.drawable.contract_home_wait_approval);
        } else if (TextUtils.equals(mContractParams.getReviewStatus(), reviewStateWebArray[1])) {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state)).setImageResource(R.drawable.contract_home_pass);
        } else {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state)).setImageResource(R.drawable.contract_home_not_pass);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        mContractParams = list.get(0);
        viewDelegate.setEntity(mContractParams);
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
        RetrofitRequest.getInstance().getContractFollow(mContractParams.getId(), null, null, null, null, null);
    }
}
