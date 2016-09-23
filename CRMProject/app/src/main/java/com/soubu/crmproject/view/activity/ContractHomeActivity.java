package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.Big4HomeActivityDelegate;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
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
public class ContractHomeActivity extends Big4HomeActivityPresenter<Big4HomeActivityDelegate> implements View.OnClickListener {
    ContractParams mContractParams;
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
        viewDelegate.setEntity(mContractParams);
        RetrofitRequest.getInstance().getCustomerSpec(mContractParams.getCustomer().getId());
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_go_left, R.id.ll_go_right);
//        viewDelegate.setSettingMenuListener(R.menu.contract_home, new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if(item.getItemId() == R.id.action_for_other){
//                    Intent intent = new Intent(ContractHomeActivity.this, ChooseEmployeeActivity.class);
//                    intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CONTRACT);
//                    startActivity(intent);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_go_left:
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
        viewDelegate.setTitle(R.string.contract_home);
        viewDelegate.setFrom(Contants.FROM_CONTRACT);
        mStateArray = getResources().getStringArray(R.array.contract_state);
        mStateArrayWeb = getResources().getStringArray(R.array.contract_state_web);
        CharSequence[] reviewStateWebArray = getResources().getStringArray(R.array.contract_review_state_web);
        mContractParams = (ContractParams) getIntent().getSerializableExtra(Contants.EXTRA_CONTRACT);
        ((TextView) viewDelegate.get(R.id.tv_title)).setText(mContractParams.getTitle());
        ((TextView) viewDelegate.get(R.id.tv_sub_left)).setText(mContractParams.getCustomer().getName());
        ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mContractParams.getStatus())]);
//        ((TextView) viewDelegate.get(R.id.tv_contract_price)).setText(mContractParams.getAmountPrice());
        if (TextUtils.equals(mContractParams.getReviewStatus(), reviewStateWebArray[0])) {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state_no_or_wait)).setImageResource(R.drawable.contract_home_wait_approval);
            viewDelegate.get(R.id.iv_contract_review_state_no_or_wait).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(mContractParams.getReviewStatus(), reviewStateWebArray[1])) {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state_yes)).setImageResource(R.drawable.contract_home_pass);
            viewDelegate.get(R.id.iv_contract_review_state_yes).setVisibility(View.VISIBLE);
        } else {
            ((ImageView) viewDelegate.get(R.id.iv_contract_review_state_no_or_wait)).setImageResource(R.drawable.contract_home_not_pass);
            viewDelegate.get(R.id.iv_contract_review_state_no_or_wait).setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        mContractParams = list.get(0);
        viewDelegate.setEntity(mContractParams);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        mCustomerParams = params[0];
        //取本地数据库中的该客户的联系人列表
        ContactDao contactDao = DBHelper.getInstance(this).getContactDao();
        List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(mContractParams.getCustomer()))
                .orderDesc(ContactDao.Properties.TouchedAt).list();
        List<ContactParams> contactList = new ArrayList<>();
        for(Contact contact : list){
            contactList.add(contact.copyToContactParams());
        }
        mLocation = mCustomerParams.getAddress();
        initConnectionView(contactList);
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
        mContractParams.setStatus(records.get(0).getStatus());
        ((TextView) viewDelegate.get(R.id.tv_sub_right)).setText(mStateArray[SearchUtil.searchInArray(mStateArrayWeb, mContractParams.getStatus())]);
        viewDelegate.setViewPagerData(0, records);
        viewDelegate.setViewPagerData(1, plans);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrofitRequest.getInstance().getContractFollow(mContractParams.getId(), null, null, null, null, null);
    }
}
