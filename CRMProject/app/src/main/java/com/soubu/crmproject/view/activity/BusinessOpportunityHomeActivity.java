package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
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
 * Created by dingsigang on 16-8-29.
 */
public class BusinessOpportunityHomeActivity extends Big4HomeActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    BusinessOpportunityParams mBusinessOpportunityParams;
    CustomerParams mCustomerParams;
    CharSequence[] mStateArray;
    CharSequence[] mStateArrayWeb;
    private final int REQUEST_TRANSFER = 1002;


    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mBusinessOpportunityParams);
        RetrofitRequest.getInstance().getCustomerSpec(mBusinessOpportunityParams.getCustomer().getId());
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_go_left, R.id.ll_go_right);
        if (!TextUtils.equals(mBusinessOpportunityParams.getStatus(), SearchUtil.searchBusinessOpportunityStateWebArray(getApplicationContext())[6])) {
            viewDelegate.setSettingMenuListener(R.menu.business_opportunity_home, new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_to_contract) {
                        transfer();
                    }
                    return false;
                }
            });
        }

    }

    private void transfer() {
        Intent intent = new Intent(BusinessOpportunityHomeActivity.this, AddContractActivity.class);
        ContractParams params = new ContractParams();
        params.settDeal(mBusinessOpportunityParams.getId());
        intent.putExtra(Contants.EXTRA_CONTRACT, params);
        intent.putExtra(Contants.EXTRA_BUSINESS_ID, mBusinessOpportunityParams.getId());
        intent.putExtra(Contants.EXTRA_BUSINESS_NAME, mBusinessOpportunityParams.getTitle());
        intent.putExtra(Contants.EXTRA_CUSTOMER_ID, mBusinessOpportunityParams.getCustomer().getId());
        intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, mBusinessOpportunityParams.getCustomer().getName());
        intent.putExtra(Contants.EXTRA_TRANSFER, true);
        startActivityForResult(intent, REQUEST_TRANSFER);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_go_left:
                Intent intent = new Intent(this, BusinessOpportunitySpecActivity.class);
                intent.putExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY, mBusinessOpportunityParams);
                startActivity(intent);
                break;
            case R.id.ll_go_right:
                Intent intent3 = new Intent(this, CustomerHomeActivity.class);
                intent3.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.business_opportunity_home);
        viewDelegate.setFrom(Contants.FROM_BUSINESS_OPPORTUNITY);
        mStateArray = getResources().getStringArray(R.array.business_opportunity_status);
        mStateArrayWeb = getResources().getStringArray(R.array.business_opportunity_status_web);
        mBusinessOpportunityParams = (BusinessOpportunityParams) getIntent().getSerializableExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mBusinessOpportunityParams.getTitle());
        ((TextView) viewDelegate.get(R.id.tv_sub_left)).setText(mBusinessOpportunityParams.getCustomer().getName());
        ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mBusinessOpportunityParams.getStatus())]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(BusinessOpportunityParams[] params) {
        List<BusinessOpportunityParams> list = Arrays.asList(params);
        mBusinessOpportunityParams = list.get(0);
        viewDelegate.setEntity(mBusinessOpportunityParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        mCustomerParams = params[0];
        //取本地数据库中的该客户的联系人列表
        ContactDao contactDao = DBHelper.getInstance(this).getContactDao();
        List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(mCustomerParams.getId()))
                .orderDesc(ContactDao.Properties.TouchedAt).list();
        List<ContactParams> contactList = new ArrayList<>();
        for (Contact contact : list) {
            contactList.add(contact.copyToContactParams());
        }
        mLocation = mCustomerParams.getAddress();
        initConnectionView(contactList);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFollow(FollowParams[] params) {
        if (!mEventBusJustForThis) {
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<FollowParams> list = Arrays.asList(params);
        if (list.size() > 0 && TextUtils.equals(list.get(0).getType(), Contants.FOLLOW_TYPE_RECORD)) {
            mBusinessOpportunityParams.setStatus(list.get(0).getStatus());
            ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mBusinessOpportunityParams.getStatus())]);
        }
        if (mRequestFollowType == REQUEST_RECORD) {
            viewDelegate.setViewPagerData(0, list);
            mRequestFollowType = REQUEST_PLAN;
            mEventBusJustForThis = true;
            RetrofitRequest.getInstance().getBusinessOpportunityFollow(mBusinessOpportunityParams.getId(), null, null, null, null, null, Contants.FOLLOW_TYPE_PLAN);
        } else {
            viewDelegate.setViewPagerData(1, list);
            mRequestFollowType = -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestFollowType == -1) {
            mEventBusJustForThis = true;
            mRequestFollowType = REQUEST_RECORD;
            //获取跟进记录
            RetrofitRequest.getInstance().getBusinessOpportunityFollow(mBusinessOpportunityParams.getId(), null, null, null, null, null, Contants.FOLLOW_TYPE_RECORD);
        }

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
                    ShowWidgetUtil.showLong(R.string.transfer_contract_succeed_message);
                    Intent intent = new Intent(this, ContractHomeActivity.class);
                    intent.putExtra(Contants.EXTRA_CONTRACT, data.getSerializableExtra(Contants.EXTRA_CONTRACT));
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
