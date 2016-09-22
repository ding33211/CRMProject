package com.soubu.crmproject.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.base.mvp.presenter.ActivityPresenter;
import com.soubu.crmproject.delegate.SearchActivityDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.server.ServerErrorUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dingsigang on 16-9-13.
 */
public class SearchActivity extends ActivityPresenter<SearchActivityDelegate> {

    private boolean mIsRefresh = false;
    private int mType = Contants.TYPE_CLUE;
    boolean mRushAction = false;


    @Override
    protected Class<SearchActivityDelegate> getDelegateClass() {
        return SearchActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mType = getIntent().getIntExtra(Contants.EXTRA_FROM, Contants.TYPE_CLUE);
        viewDelegate.setType(mType);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        ((EditText)viewDelegate.get(R.id.et_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(TextUtils.isEmpty(s.toString())){
//                    viewDelegate.clear();
//                }
                RetrofitRequest request = RetrofitRequest.getInstance();
                switch (mType){
                    case Contants.FROM_CLUE:
                        request.getClueList(null, null, null, null, null, null, null, s.toString());
                        mIsRefresh = true;
                        break;
                    case Contants.FROM_CUSTOMER:
                        request.getCustomerList(null, null, null, null, null, null, null, null, null, null, s.toString());
                        mIsRefresh = true;
                        break;
                    case Contants.FROM_BUSINESS_OPPORTUNITY:
                        request.getBusinessOpportunityList(null, null, null, null, null, null, null, null, null, s.toString());
                        mIsRefresh = true;
                        break;
                    case Contants.FROM_CONTRACT:
                        request.getContractList(null, null, null, null, null, null, null, null, null, null, s.toString());
                        mIsRefresh= true;
                        break;
                    case Contants.FROM_CLUE_HIGH_SEAS:
                        request.getClueHighSeasList(null, null, null, null, null, null, s.toString());
                        mIsRefresh = true;
                        break;
                }
            }
        });

        switch (mType){
            case Contants.FROM_CLUE:
                viewDelegate.setOnClueClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Intent intent = new Intent(SearchActivity.this, ClueHomeActivity.class);
                        intent.putExtra(Contants.EXTRA_CLUE, viewDelegate.getClueParams(pos));
                        startActivity(intent);
                    }
                });
                break;
            case Contants.FROM_CUSTOMER:
                viewDelegate.setOnCustomerClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Intent intent = new Intent(SearchActivity.this, CustomerHomeActivity.class);
                        intent.putExtra(Contants.EXTRA_CUSTOMER, viewDelegate.getCustomerParams(pos));
                        startActivity(intent);
                    }
                });
                break;
            case Contants.FROM_BUSINESS_OPPORTUNITY:
                viewDelegate.setOnBusinessOpportunityClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Intent intent = new Intent(SearchActivity.this, BusinessOpportunityHomeActivity.class);
                        intent.putExtra(Contants.EXTRA_BUSINESS_OPPORTUNITY, viewDelegate.getBusinessOpportunityParams(pos));
                        startActivity(intent);
                    }
                });
                break;
            case Contants.FROM_CONTRACT:
                viewDelegate.setOnContractClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Intent intent = new Intent(SearchActivity.this, ContractHomeActivity.class);
                        intent.putExtra(Contants.EXTRA_CONTRACT, viewDelegate.getContractParams(pos));
                        startActivity(intent);
                    }
                });
                break;
            case Contants.FROM_CLUE_HIGH_SEAS:
                viewDelegate.setOnClueClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Intent intent = new Intent(SearchActivity.this, ClueHomeActivity.class);
                        intent.putExtra(Contants.EXTRA_CLUE, viewDelegate.getClueParams(pos));
                        startActivity(intent);
                    }
                });

                viewDelegate.setOnClueRushClickListener(new BaseWithFooterRvAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        RetrofitRequest.getInstance().rushClue(viewDelegate.getClueParams(pos).getId());
                        mRushAction = true;
                    }
                });
                break;
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ClueParams[] params) {
        if (mRushAction) {
            if (params != null && params.length > 0) {
                final ClueParams params1 = params[0];
                new android.app.AlertDialog.Builder(this).setMessage(getString(R.string.succeed_rush_clue_message, params1.getCompanyName()))
                        .setPositiveButton(R.string.look_clue_spec, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SearchActivity.this, ClueSpecActivity.class);
                                intent.putExtra(Contants.EXTRA_CLUE, params1);
                                intent.putExtra(Contants.EXTRA_FROM, Contants.FROM_CLUE_HIGH_SEAS);
                                startActivity(intent);
                            }
                        }).setNegativeButton(R.string.continue_rush, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitRequest.getInstance().getClueHighSeasList(null, null, null, null, null, null, ((EditText)viewDelegate.get(R.id.et_search)).getText().toString());
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
            }
            mRushAction = false;
        } else {
            List<ClueParams> list = Arrays.asList(params);
            viewDelegate.setClue(list, mIsRefresh);
            if (mIsRefresh) {
                mIsRefresh = false;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(CustomerParams[] params) {
        List<CustomerParams> list = Arrays.asList(params);
        viewDelegate.setCustomer(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(BusinessOpportunityParams[] params) {
        List<BusinessOpportunityParams> list = Arrays.asList(params);
        viewDelegate.setBusinessOpportunity(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(ContractParams[] params) {
        List<ContractParams> list = Arrays.asList(params);
        viewDelegate.setContract(list, mIsRefresh);
        if (mIsRefresh) {
            mIsRefresh = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void throwError(Integer errorCode) {
        ServerErrorUtil.handleServerError(errorCode);
    }

}
