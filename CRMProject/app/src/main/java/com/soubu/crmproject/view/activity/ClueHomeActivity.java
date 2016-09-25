package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-22.
 */
public class ClueHomeActivity extends Big4HomeActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    ClueParams mClueParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateArrayWeb;

    private final int REQUEST_CHOOSE_EMPLOYEE = 1001;
    private final int REQUEST_TRANSFER = 1002;

    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mClueParams);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_go_left);
        viewDelegate.setSettingMenuListener(R.menu.clue_home, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_for_other) {
                    Intent intent = new Intent(ClueHomeActivity.this, ChooseEmployeeActivity.class);
                    intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
                    intent.putExtra(Contants.EXTRA_PARAM_ID, mClueParams.getId());
                    startActivityForResult(intent, REQUEST_CHOOSE_EMPLOYEE);
                }
                if (item.getItemId() == R.id.action_to_customer) {
                    transfer();
                }
                return false;
            }
        });
    }

    private void transfer() {
        Intent intent = new Intent(ClueHomeActivity.this, AddCustomerActivity.class);
        CustomerParams params = new CustomerParams();
        params.setName(mClueParams.getCompanyName());
        params.setAddress(mClueParams.getAddress());
        params.setWebsite(mClueParams.getWebsite());
        params.setSource(mClueParams.getSource());
        params.setManager(mClueParams.getManager());
        params.settOpportunity(mClueParams.getId());
        intent.putExtra(Contants.EXTRA_CUSTOMER, params);
        intent.putExtra(Contants.EXTRA_TRANSFER, true);
        startActivityForResult(intent, REQUEST_TRANSFER);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_go_left:
                Intent intent = new Intent(this, ClueSpecActivity.class);
                intent.putExtra(Contants.EXTRA_CLUE, mClueParams);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.clue_home);
        viewDelegate.setFrom(Contants.FROM_CLUE);
        mStateArray = getResources().getStringArray(R.array.clue_status);
        mStateArrayWeb = getResources().getStringArray(R.array.clue_status_web);
        ClueParams param = (ClueParams) getIntent().getSerializableExtra(Contants.EXTRA_CLUE);
        initView(param);

    }


    private void initView(ClueParams clueParams) {
        mClueParams = clueParams;
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(clueParams.getCompanyName());
        ((TextView) viewDelegate.get(R.id.tv_sub_left)).setText(clueParams.getContactName());
        ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, clueParams.getStatus())]);
        mPhoneList.clear();
        mEmailList.clear();
        mWechatList.clear();
        mQqList.clear();
        mLocation = clueParams.getAddress();
        if (!TextUtils.isEmpty(clueParams.getMobile())) {
            DialogItem item = new DialogItem();
            item.value = clueParams.getMobile();
            mPhoneList.add(item);
        }
        if (!TextUtils.isEmpty(clueParams.getPhone())) {
            DialogItem item = new DialogItem();
            item.value = clueParams.getPhone();
            mPhoneList.add(item);
        }
        if (!TextUtils.isEmpty(clueParams.getWechat())) {
            DialogItem item = new DialogItem();
            item.value = clueParams.getWechat();
            mWechatList.add(item);
        }
        if (!TextUtils.isEmpty(clueParams.getEmail())) {
            DialogItem item = new DialogItem();
            item.value = clueParams.getEmail();
            mEmailList.add(item);
        }
        if (!TextUtils.isEmpty(clueParams.getAddress())) {
            mLocation = clueParams.getAddress();
        }
        if (!TextUtils.isEmpty(clueParams.getQq()) && !TextUtils.equals(clueParams.getQq(), "0")) {
            DialogItem item = new DialogItem();
            item.value = clueParams.getQq();
            mQqList.add(item);
        }
        initConnectionView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        List<ClueParams> list = Arrays.asList(params);
        viewDelegate.setEntity(list.get(0));
        initView(list.get(0));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFollow(FollowParams[] params) {
        if (!mEventBusJustForThis) {
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<FollowParams> list = Arrays.asList(params);
        List<FollowParams> records = new ArrayList<>();
        List<FollowParams> plans = new ArrayList<>();
        for (FollowParams param : list) {
            if (TextUtils.equals(param.getType(), Contants.FOLLOW_TYPE_PLAN)) {
                plans.add(param);
            } else {
                records.add(param);
            }
        }
        if(records.size() > 0){
            mClueParams.setStatus(records.get(0).getStatus());
            ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mClueParams.getStatus())]);
        }
        viewDelegate.setViewPagerData(0, records);
        viewDelegate.setViewPagerData(1, plans);
    }




    @Override
    protected void onResume() {
        super.onResume();
        mEventBusJustForThis = true;
        RetrofitRequest.getInstance().getClueFollow(mClueParams.getId(), null, null, null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Big4HomeActivityDelegate.REQUEST_ADD_FOLLOW:
                    transfer();
                    break;
                case REQUEST_TRANSFER:
                    Intent intent = new Intent(this, CustomerHomeActivity.class);
                    intent.putExtra(Contants.EXTRA_CUSTOMER, data.getSerializableExtra(Contants.EXTRA_CUSTOMER));
                    startActivity(intent);
                    finish();
                    break;
                case REQUEST_CHOOSE_EMPLOYEE:
                    ShowWidgetUtil.showLong(getString(R.string.transfer_success_message, mClueParams.getCompanyName(), data.getStringExtra(Contants.EXTRA_TRANSFER_NAME)));
                    finish();
                    break;
            }
        }
    }
}
