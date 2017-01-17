package com.soubu.crmproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.delegate.CustomerActivityDelegate;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivity extends Big4AllActivityPresenter<CustomerActivityDelegate> {

    String mProperty;
    String mSource;
    String mStatus;
    String mType;
    String mSize;
    String mIndustry;
    String mSort;
    String mOrder;
    String mRelated;
    boolean mRushAction = false;


    @Override
    protected Class<CustomerActivityDelegate> getDelegateClass() {
        return CustomerActivityDelegate.class;
    }

    /**
     * 监听Clue请求回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        if (mRushAction) {
            if (params != null && params.length > 0) {
                final CustomerParams params1 = params[0];
                new android.app.AlertDialog.Builder(this).setMessage(getString(R.string.succeed_rush_customer_message, params1.getName()))
                        .setPositiveButton(R.string.look_customer_spec, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CustomerActivity.this, DamnCustomerActivity.class);
                                intent.putExtra(Contants.EXTRA_CUSTOMER, params1);
                                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER_HIGH_SEAS);
                                startActivity(intent);
                            }
                        }).setNegativeButton(R.string.continue_rush, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getList(true);
                    }
                }).setCancelable(false).show();
            }
            mRushAction = false;
        } else {
            List<CustomerParams> list = Arrays.asList(params);
            viewDelegate.setData(list, mIsRefresh);
            if (mIsRefresh) {
                mIsRefresh = false;
                viewDelegate.stopSwipeRefresh();
            }
        }
    }

    @Override
    protected int getParentArray() {
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            return R.array.customer_high_seas_filter;
        } else {
            return R.array.customer_filter;
        }
    }

    @Override
    protected String[][] getChildrenArray() {
        return new String[][]{getResources().getStringArray(R.array.customer_property),getResources().getStringArray(R.array.customer_type),
                getResources().getStringArray(R.array.clue_source), getResources().getStringArray(R.array.customer_size),
                getResources().getStringArray(R.array.customer_industry), getResources().getStringArray(R.array.customer_status),
                getResources().getStringArray(R.array.clue_related)};
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setHighSeas(mFrom == Contants.TYPE_HIGH_SEAS);

    }

    @Override
    protected int getSortArray() {
        return R.array.clue_sort;
    }

    @Override
    protected void doRequest(int pageNum) {
        mEventBusJustForThis = true;
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            RetrofitRequest.getInstance().getCustomerHighSeasList(pageNum, mType, mSource, mSize, mIndustry, mStatus, mSort, mOrder, null, null);
        } else {
            RetrofitRequest.getInstance().getCustomerList(pageNum, mType, mSource, mSize, mIndustry, mStatus, mSort, mOrder, mRelated, null, null);
        }


    }

    @Override
    protected void onRvItemClickListener(View v, int pos) {
        CustomerParams params = viewDelegate.getCustomerParams(pos);
        if (mFrom == Contants.FROM_ADD_SOMETHING_ACTIVITY) {
            Intent intent = new Intent();
            intent.putExtra(Contants.EXTRA_CUSTOMER_ID, params.getId());
            intent.putExtra(Contants.EXTRA_CUSTOMER_NAME, params.getName());
            setResult(RESULT_OK, intent);
            finish();
        } else if (mFrom == Contants.TYPE_HIGH_SEAS) {
            Intent intent = new Intent(this, DamnCustomerActivity.class);
            intent.putExtra(Contants.EXTRA_CUSTOMER, params);
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER_HIGH_SEAS);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CustomerHomeActivity.class);
            intent.putExtra(Contants.EXTRA_CUSTOMER, params);
            startActivity(intent);
        }
    }

    @Override
    public void onRushClickListener(View v, int pos) {
        mEventBusJustForThis = true;
        RetrofitRequest.getInstance().rushCustomer(viewDelegate.getCustomerParams(pos).getId());
        mRushAction = true;
    }

    @Override
    protected void onSelectFilter(Map<Integer, Integer> map) {
        if (map.isEmpty()) {
            mProperty = null;
            mSource = null;
            mStatus = null;
            mType = null;
            mSize = null;
            mIndustry = null;
        } else {
            String[] strings0 = getResources().getStringArray(R.array.customer_type_web);
            String[] strings1 = getResources().getStringArray(R.array.clue_source_web);
            String[] strings2 = getResources().getStringArray(R.array.customer_size_web);
            String[] strings3 = getResources().getStringArray(R.array.customer_industry_web);
            String[] strings4 = getResources().getStringArray(R.array.customer_status_web);
            String[] strings5 = getResources().getStringArray(R.array.clue_related_web);

            if (map.containsKey(0)) {
                if (map.get(0) == 0) {
                    mType = null;
                } else {
                    mType = strings0[map.get(0) - 1];
                }
            }
            if (map.containsKey(1)) {
                if (map.get(1) == 0) {
                    mSource = null;
                } else {
                    mSource = strings1[map.get(1) - 1];
                }
            }
            if (map.containsKey(2)) {
                if (map.get(2) == 0) {
                    mSize = null;
                } else {
                    mSize = strings2[map.get(2) - 1];
                }
            }
            if (map.containsKey(3)) {
                if (map.get(3) == 0) {
                    mIndustry = null;
                } else {
                    mIndustry = strings3[map.get(3) - 1];
                }
            }
            if (map.containsKey(4)) {
                if (map.get(4) == 0) {
                    mStatus = null;
                } else {
                    mStatus = strings4[map.get(4) - 1];
                }
            }
            if (map.containsKey(5)) {
                if (map.get(5) == 0) {
                    mRelated = null;
                } else {
                    mRelated = strings5[map.get(5) - 1];
                }
            }
        }
        mIsRefresh = true;
        doRequest(1);
    }

    @Override
    protected void onSelectSort(int pos) {
        switch (pos) {
            case 0:
                mSort = "CREATED_AT";
                mOrder = "ASC";
                break;
            case 1:
                mSort = "CREATED_AT";
                mOrder = "DESC";
                break;
            case 2:
                mSort = "UPDATED_AT";
                mOrder = "ASC";
                break;
            case 3:
                mSort = "CREATED_AT";
                mOrder = "DESC";
                break;
        }
        mIsRefresh = true;
        doRequest(1);
    }

    @Override
    protected void onClickAdd(View v) {
        Intent intent = new Intent(this, AddCustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClickSearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        if (mFrom == Contants.TYPE_HIGH_SEAS) {
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER_HIGH_SEAS);
        } else {
            intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER);
        }
        startActivity(intent);
    }

}
