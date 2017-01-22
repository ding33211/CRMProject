package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-26.
 */
public class CustomerHomeActivity extends Big4HomeActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    CustomerParams mCustomerParams;
    private final int REQUEST_CHOOSE_EMPLOYEE = 1001;
    private int mDealsCount = -1;
    private int mContractCount = -1;
    private int mFrom;
    private CharSequence[] mPropertyWebArray;


    @Override
    protected Class<Big4HomeActivityDelegate> getDelegateClass() {
        return Big4HomeActivityDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setTitle(R.string.customer_home);
        viewDelegate.setFrom(Contants.FROM_CUSTOMER);
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, -1);
        mPropertyWebArray = SearchUtil.searchCustomerPropertyWebArray(this);
        if (mFrom != -1) {
            viewDelegate.get(R.id.rl_bottom).setVisibility(View.GONE);
        }
        mCustomerParams = (CustomerParams) getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER);
//        mDealsCount = Integer.valueOf(mCustomerParams.getDealsCount());
//        mContractCount = Integer.valueOf(mCustomerParams.getContractsCount());
        refreshCustomerContent();
    }


    private void refreshCustomerContent() {
        ((TextView) viewDelegate.get(R.id.tv_company)).setText(mCustomerParams.getName());
        String contactName = mCustomerParams.getContactName();
        if (TextUtils.isEmpty(contactName)) {
            viewDelegate.get(R.id.v_mid_line_sub_1).setVisibility(View.GONE);
            ((TextView) viewDelegate.get(R.id.tv_sub_right_1)).setText(mCustomerParams.getAddress());
        } else {
            ((TextView) viewDelegate.get(R.id.tv_sub_left_1)).setText(mCustomerParams.getContactName());
            ((TextView) viewDelegate.get(R.id.tv_sub_right_1)).setText(mCustomerParams.getAddress());
        }
        String buyOrderCount = mCustomerParams.getBuyOrdersCount();
        String sellOrderCount = mCustomerParams.getSellOrdersCount();
        String buyAmount = mCustomerParams.getBuyOrdersAmount();
        String sellAmount = mCustomerParams.getSellOrdersAmount();
        int property = SearchUtil.searchInArray(mPropertyWebArray, mCustomerParams.getProperty());
        if (property == 0) {
            ((TextView) viewDelegate.get(R.id.tv_order_count)).setText(sellOrderCount);
            ((TextView) viewDelegate.get(R.id.tv_order_turnover)).setText(sellAmount);
        } else {
            ((TextView) viewDelegate.get(R.id.tv_order_count)).setText(buyOrderCount);
            ((TextView) viewDelegate.get(R.id.tv_order_turnover)).setText(buyAmount);
        }
        ((TextView) viewDelegate.get(R.id.tv_useful_follow)).setText(mCustomerParams.getRecordsCount());

    }

    private void initCountView() {
        ((TextView) viewDelegate.get(R.id.tv_left_number)).setText(mDealsCount + "");
        ((TextView) viewDelegate.get(R.id.tv_right_number)).setText(mContractCount + "");
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.setEntity(mCustomerParams);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (mFrom == -1) {
            viewDelegate.setSettingMenuListener(R.menu.customer_home, new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_for_other:
                            Intent intent = new Intent(CustomerHomeActivity.this, ChooseEmployeeActivity.class);
                            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER);
                            intent.putExtra(Contants.EXTRA_PARAM_ID, mCustomerParams.getId());
                            startActivityForResult(intent, REQUEST_CHOOSE_EMPLOYEE);
                            break;
                        case R.id.action_to_customer_high_seas:
//                        if (!TextUtils.equals(mCustomerParams.getDealsCount(), "0") || !TextUtils.equals(mCustomerParams.getContractsCount(), "0")) {
//                            ShowWidgetUtil.showShort(R.string.throw_to_customer_high_sea_unable_message);
//                        } else {
                            RetrofitRequest.getInstance().throwToCustomerHighSeas(mCustomerParams.getId());
//                        }
                    }
                    return false;
                }
            });
        }
        viewDelegate.setOnClickListener(this, R.id.ll_go_left, R.id.ll_go_right, R.id.ll_left, R.id.ll_right, R.id.ll_customer_home);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_go_left:
            case R.id.ll_customer_home:
                Intent intent = new Intent(this, DamnCustomerActivity.class);
                intent.putExtra(Contants.EXTRA_CUSTOMER, mCustomerParams);
                intent.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
                intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, mCustomerParams.getName());
                startActivity(intent);
                break;
            //样式修改已禁用
            case R.id.ll_go_right:
//                Intent intent1 = new Intent(this, ContactActivity.class);
//                intent1.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
//                intent1.putExtra(Contants.EXTRA_CUSTOMER_NAME, mCustomerParams.getName());
//                startActivity(intent1);
                break;
            case R.id.ll_left:
                Intent intent2 = new Intent(this, BusinessOpportunityActivity.class);
                intent2.putExtra(Contants.EXTRA_CUSTOMER_ID, mCustomerParams.getId());
                intent2.putExtra(Contants.EXTRA_CUSTOMER_NAME, mCustomerParams.getName());
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
        //说明是转入客户公海成功
        if (params.length == 0) {
            ShowWidgetUtil.showShort(R.string.throw_to_customer_high_sea_success_message);
            finish();
            return;
        }
        List<CustomerParams> list = Arrays.asList(params);
        mCustomerParams = list.get(0);
        viewDelegate.setEntity(mCustomerParams);
        refreshCustomerContent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFollow(FollowParams[] params) {
        List<FollowParams> list = Arrays.asList(params);
        if (mRequestFollowType == REQUEST_RECORD) {
            viewDelegate.setViewPagerData(0, list);
            mRequestFollowType = REQUEST_PLAN;
            mEventBusJustForThis = true;
            RetrofitRequest.getInstance().getCustomerFollow(mCustomerParams.getId(), null, null, null, null, null, Contants.FOLLOW_TYPE_PLAN);
        } else {
            viewDelegate.setViewPagerData(1, list);
            mRequestFollowType = -1;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContactParams[] params) {
        Log.e("xxxxxxx", "    params.length   :  " + params.length);
        if (params != null && params.length > 0) {
            mLocation = mCustomerParams.getAddress();
            ContactDao contactDao = DBHelper.getInstance(this).getContactDao();
            for (ContactParams param : params) {
                if (TextUtils.isEmpty(param.getId())) {
                    continue;
                }
                List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Contact_id.eq(param.getId())).list();
                if (list == null || list.size() == 0) {
                    contactDao.insert(param.copyToContact());
                } else {
                    if (!param.equalsContact(list.get(0))) {
                        Contact contact = param.copyToContact();
                        contact.setId(list.get(0).getId());
                        contactDao.update(contact);
                    }
                }
            }
            initConnectionView(Arrays.asList(params));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(Integer key) {
        if (key == Contants.EVENT_BUS_KEY_ADD_BUSINESS) {
            mDealsCount++;
        } else if (key == Contants.EVENT_BUS_KEY_ADD_CONTRACT) {
            mContractCount++;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventBusJustForThis = true;
        if (mRequestFollowType == -1) {
            mRequestFollowType = REQUEST_RECORD;
            //获取跟进记录
            RetrofitRequest.getInstance().getCustomerFollow(mCustomerParams.getId(), null, null, null, null, null, Contants.FOLLOW_TYPE_RECORD);
        }
        //获取客户名下联系人
        RetrofitRequest.getInstance().getContactList(null, mCustomerParams.getId());
        initCountView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_EMPLOYEE:
                    ShowWidgetUtil.showLong(getString(R.string.transfer_success_message, mCustomerParams.getName(), data.getStringExtra(Contants.EXTRA_TRANSFER_NAME)));
                    finish();
            }
        }
    }
}
