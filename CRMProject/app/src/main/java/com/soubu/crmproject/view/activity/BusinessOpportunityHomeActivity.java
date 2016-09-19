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
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.utils.SearchUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-8-29.
 */
public class BusinessOpportunityHomeActivity extends Big4HomeActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener{
    BusinessOpportunityParams mBusinessOpportunityParams;
    CustomerParams mCustomerParams;
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
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_go_left, R.id.ll_go_right);
        viewDelegate.setSettingMenuListener(R.menu.business_opportunity_home, new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
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
        ((TextView) viewDelegate.get(R.id.tv_sub_left)).setText(mBusinessOpportunityParams.getCustomer());
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
        List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(mBusinessOpportunityParams.getCustomer()))
                .orderDesc(ContactDao.Properties.TouchedAt).list();
        List<ContactParams> contactList = new ArrayList<>();
        for(Contact contact : list){
            contactList.add(contact.copyToContactParams());
        }
        mLocation = mCustomerParams.getAddress();
        initConnectionView(contactList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
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
        RetrofitRequest.getInstance().getBusinessOpportunityFollow(mBusinessOpportunityParams.getId(), null, null, null, null, null);
        RetrofitRequest.getInstance().getCustomerSpec(mBusinessOpportunityParams.getCustomer());

    }
}
