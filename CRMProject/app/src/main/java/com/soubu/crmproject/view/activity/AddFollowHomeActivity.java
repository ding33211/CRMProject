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
import com.soubu.crmproject.server.ServerErrorUtil;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-5.
 */
public class AddFollowHomeActivity extends ActivityPresenter<AddFollowHomeActivityDelegate> {
    int mType = Contants.TYPE_CLUE;
    int mCluePage = 1;
    int mCustomerPage = 1;
    int mContractPage = 1;
    int mBusinessOpportunityPage = 1;
    int mFollowType = -1;

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
        mFollowType = getIntent().getIntExtra(Contants.EXTRA_TYPE, -1);
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
                onRecyclerItemClick(Contants.FROM_CLUE, viewDelegate.getClueParams(pos));
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                onRecyclerItemClick(Contants.FROM_CUSTOMER, viewDelegate.getCustomerParams(pos));
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                onRecyclerItemClick(Contants.FROM_BUSINESS_OPPORTUNITY, viewDelegate.getBusinessOpportunityParams(pos));
            }
        }, new BaseWithFooterRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                onRecyclerItemClick(Contants.FROM_CONTRACT, viewDelegate.getContractParams(pos));
            }
        });

        viewDelegate.set4OnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClick(Contants.FROM_CLUE);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClick(Contants.FROM_CUSTOMER);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClick(Contants.FROM_BUSINESS_OPPORTUNITY);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemClick(Contants.FROM_CONTRACT);
            }
        });
    }

    private void onRecyclerItemClick(int from, Serializable entity){
        Intent intent = new Intent(AddFollowHomeActivity.this, AddFollowActivity.class);
        intent.putExtra(Contants.EXTRA_FROM, from);
        intent.putExtra(Contants.EXTRA_TYPE, mFollowType);
        intent.putExtra(Contants.EXTRA_ENTITY, entity);
        intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
        if (mFromAddFollow) {
            setResult(RESULT_OK, intent);
            finish();
        } else {
            startActivity(intent);
        }
    }

    private void onSearchItemClick(int from){
        Intent intent = new Intent(AddFollowHomeActivity.this, SearchActivity.class);
        intent.putExtra(Contants.EXTRA_FROM, from);
        intent.putExtra(Contants.EXTRA_FROM_ADD_FOLLOW_HOME, true);
        startActivity(intent);
    }

    private void getList(boolean isRefresh, int type, int pageNum) {
        mType = type;
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
                mEventBusJustForThis = true;
                break;
            case Contants.TYPE_CUSTOMER:
                request.getCustomerList(pageNum, null, null, null, null, null, null, null, null, null, null);
                mEventBusJustForThis = true;
                break;
            case Contants.TYPE_BUSINESS_OPPORTUNITY:
                request.getBusinessOpportunityList(pageNum, null, null, null, null, null, null, null, null, null);
                mEventBusJustForThis = true;
                break;
            case Contants.TYPE_CONTRACT:
                request.getContractList(pageNum, null, null, null, null, null, null, null, null, null, null, null);
                mEventBusJustForThis = true;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(true, mType, 1);
        haveInit[0] = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshClueData(ClueParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<ClueParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CLUE);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getClueDataFromSearch(ClueParams param) {
        onRecyclerItemClick(Contants.FROM_CLUE, param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCustomerData(CustomerParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<CustomerParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    CustomerParams    refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CUSTOMER);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCustomerDataFromSearch(CustomerParams param) {
        onRecyclerItemClick(Contants.FROM_CUSTOMER, param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBusinessData(BusinessOpportunityParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<BusinessOpportunityParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    BusinessOpportunityParams     refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_BUSINESS_OPPORTUNITY);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBusinessDataFromSearch(BusinessOpportunityParams param) {
        onRecyclerItemClick(Contants.FROM_BUSINESS_OPPORTUNITY, param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshContractData(ContractParams[] params) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        List<ContractParams> list = Arrays.asList(params);
        Log.e("xxxxxxxxxx", "    ContractParams     refreshData     mIsRefresh   " + mIsRefresh);
        viewDelegate.setData(list, mIsRefresh, Contants.TYPE_CONTRACT);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getContractDataFromSearch(ContractParams param) {
        onRecyclerItemClick(Contants.FROM_CONTRACT, param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        if(!mEventBusJustForThis){
            return;
        } else {
            mEventBusJustForThis = false;
        }
        ServerErrorUtil.handleServerError(errorCode);
        if (mIsRefresh) {
            mIsRefresh = false;
            viewDelegate.stopRefresh();
        }
    }


}
