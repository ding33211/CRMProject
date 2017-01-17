package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.DamnCustomerActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.view.fragment.ContactFragment;
import com.soubu.crmproject.view.fragment.CustomerSpecFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 17-1-17.
 */

public class DamnCustomerActivity extends ActivityPresenter<DamnCustomerActivityDelegate> {


    CustomerSpecFragment mCustomerSpecFragment;
    ContactFragment mContactFragment;

    boolean mIfHighSeas = false;
    int mFrom = 0;
    @Override
    protected Class<DamnCustomerActivityDelegate> getDelegateClass() {
        return DamnCustomerActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.customer_spec);
        mFrom = getIntent().getIntExtra(Contants.EXTRA_FROM, -1);
        mIfHighSeas = mFrom == Contants.FROM_CUSTOMER_HIGH_SEAS;
    }

    @Override
    protected void initData() {
        super.initData();
        String customerId = getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID);
        List<Fragment> fragments = new ArrayList<>();
        mCustomerSpecFragment = new CustomerSpecFragment();
        mCustomerSpecFragment.setIfHighSeas(mIfHighSeas);
        mContactFragment = new ContactFragment();
        mContactFragment.setPublicArgument(mFrom, customerId);
        fragments.add(mCustomerSpecFragment);
        fragments.add(mContactFragment);
        String[] titles = new String[]{getString(R.string.customer_spec), getString(R.string.contact)};
        viewDelegate.initFragment(fragments, titles);
        bindTabOnClickListener();
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (!mIfHighSeas) {
            viewDelegate.setSettingText(R.string.edit, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DamnCustomerActivity.this, AddCustomerActivity.class);
                    intent.putExtra(Contants.EXTRA_CUSTOMER, getIntent().getSerializableExtra(Contants.EXTRA_CUSTOMER));
                    startActivity(intent);
                }
            });
        }

        if (mFrom != Contants.FROM_ADD_SOMETHING_ACTIVITY) {
            viewDelegate.setRightMenuOne(R.drawable.btn_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                                mGoto = GO_TO_ADD;
                    Intent intent = new Intent(DamnCustomerActivity.this, AddContactActivity.class);
                    intent.putExtra(Contants.EXTRA_CUSTOMER_ID, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_ID));
                    intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, getIntent().getStringExtra(Contants.EXTRA_CUSTOMER_NAME));
                    startActivity(intent);
                }
            });
        }
        viewDelegate.setSettingTextVisibility(View.VISIBLE);
        viewDelegate.setRightMenuOneVisibility(View.GONE);

    }

    private void bindTabOnClickListener(){
        viewDelegate.setOnTabClickListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    viewDelegate.setTitle(R.string.customer_spec);
                    if (!mIfHighSeas) {
                        viewDelegate.setSettingTextVisibility(View.VISIBLE);
                        viewDelegate.setRightMenuOneVisibility(View.GONE);
                    }
                } else {
                    viewDelegate.setTitle(R.string.contact);
                    if (mFrom != Contants.FROM_ADD_SOMETHING_ACTIVITY) {
                        viewDelegate.setSettingTextVisibility(View.GONE);
                        viewDelegate.setRightMenuOneVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}
