package com.soubu.crmproject.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.AddFollowHomeActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-5.
 */
public class AddFollowHomeActivity extends ActivityPresenter<AddFollowHomeActivityDelegate> {
    int mtype = Contants.TYPE_CLUE;
    int mCluePage = 1;
    int mCustomerPage = 1;
    int mContractPage = 1;
    int mBusinessOpportunityPage = 1;
    boolean mIsRefresh = false;
    //是否来自再次选择对象
    boolean mFromAddFollow = false;
    //是否有加载过
    Boolean[] haveInit = new Boolean[]{false, false, false, false};


    @Override
    protected Class<AddFollowHomeActivityDelegate> getDelegateClass() {
        return AddFollowHomeActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.please_choose_follow_object);
        mFromAddFollow = getIntent().getBooleanExtra(Contants.EXTRA_FROM_ADD_FOLLOW, false);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.set4SwipeRefreshCallBack(new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true, Contants.TYPE_CLUE, mCluePage);
            }

            @Override
            public void loadMore() {
                getList(false, Contants.TYPE_CLUE, mCluePage);
            }
        }, new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true, Contants.TYPE_CUSTOMER, mCustomerPage);
            }

            @Override
            public void loadMore() {
                getList(false, Contants.TYPE_CUSTOMER, mCustomerPage);
            }
        }, new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true, Contants.TYPE_BUSINESS_OPPORTUNITY, mBusinessOpportunityPage);
            }

            @Override
            public void loadMore() {
                getList(false, Contants.TYPE_BUSINESS_OPPORTUNITY, mBusinessOpportunityPage);
            }
        }, new SwipeRefreshAndLoadMoreCallBack() {
            @Override
            public void refresh() {
                getList(true, Contants.TYPE_CONTRACT, mContractPage);
            }

            @Override
            public void loadMore() {
                getList(false, Contants.TYPE_CONTRACT, mContractPage);
            }
        });

        viewDelegate.setOnPageChangeListener(new AddFollowHomeActivityDelegate.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPos) {
                if (haveInit[currentPos]) {
                    return;
                }
                switch (currentPos) {
                    case 0:
                        getList(true, Contants.TYPE_CLUE, 1);
                        break;
                    case 1:
                        getList(true, Contants.TYPE_CUSTOMER, 1);
                        break;
                    case 2:
                        getList(true, Contants.TYPE_BUSINESS_OPPORTUNITY, 1);
                        break;
                    case 3:
                        getList(true, Contants.TYPE_CONTRACT, 1);
                        break;
                }
                haveInit[currentPos] = true;
            }
        });
        viewDelegate.setOnRecyclerViewItemClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(AddFollowHomeActivity.this, AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_RECORD);
                intent.putExtra(Contants.EXTRA_ENTITY, viewDelegate.getClueParams(pos));
                intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
                if (mFromAddFollow) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    startActivity(intent);
                }
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(AddFollowHomeActivity.this, AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CUSTOMER);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_RECORD);
                intent.putExtra(Contants.EXTRA_ENTITY, viewDelegate.getCustomerParams(pos));
                intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
                if (mFromAddFollow) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    startActivity(intent);
                }
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(AddFollowHomeActivity.this, AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_BUSINESS_OPPORTUNITY);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_RECORD);
                intent.putExtra(Contants.EXTRA_ENTITY, viewDelegate.getBusinessOpportunityParams(pos));
                intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
                if (mFromAddFollow) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    startActivity(intent);
                }
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent(AddFollowHomeActivity.this, AddFollowActivity.class);
                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CONTRACT);
                intent.putExtra(Contants.EXTRA_TYPE, AddFollowActivity.TYPE_RECORD);
                intent.putExtra(Contants.EXTRA_ENTITY, viewDelegate.getContractParams(pos));
                intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
                if (mFromAddFollow) {
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void getList(boolean isRefresh, int type, int pageNum) {
        mtype = type;
        mIsRefresh = isRefresh;
        if (isRefresh) {
            pageNum = 1;
        } else {
            pageNum++;
        }
        doRequest(pageNum, type);
    }

    private void doRequest(int pageNum, int type) {
        RetrofitRequest request = RetrofitRequest.getInstance();
        switch (type) {
            case Contants.TYPE_CLUE:
                request.getClueList(pageNum, null, null, null, null, null, null, null);
                break;
            case Contants.TYPE_CUSTOMER:
                request.getCustomerList(pageNum, null, null, null, null, null, null, null, null, null, null);
                break;
            case Contants.TYPE_BUSINESS_OPPORTUNITY:
                request.getBusinessOpportunityList(pageNum, null, null, null, null, null, null, null, null, null);
                break;
            case Contants.TYPE_CONTRACT:
                request.getContractList(pageNum, null, null, null, null, null, null, null, null, null, null);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(true, mtype, 1);
        haveInit[0] = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshClueData(ClueParams[] params) {
        List<ClueParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CLUE);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCustomerData(CustomerParams[] params) {
        List<CustomerParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    CustomerParams    refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CUSTOMER);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBusinessData(BusinessOpportunityParams[] params) {
        List<BusinessOpportunityParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    BusinessOpportunityParams     refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_BUSINESS_OPPORTUNITY);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshContractData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    ContractParams     refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CONTRACT);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Throwable t) {

    }


}
