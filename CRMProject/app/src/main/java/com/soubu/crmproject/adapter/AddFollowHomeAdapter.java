package com.soubu.crmproject.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;

import java.util.List;

/**
 * Created by dingsigang on 16-9-5.
 */
public class AddFollowHomeAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
    String[] mTabs;
    Context mContext;
    ClueRvAdapter mClueAdapter;
    CustomerRvAdapter mCustomerAdapter;
    BusinessOpportunityRvAdapter mBusinessOpportunityAdapter;
    ContractRvAdapter mContractAdapter;
    SwipeRefreshLayout mSrlContainer;
    SwipeRefreshAndLoadMoreCallBack mClueCallBack;
    SwipeRefreshAndLoadMoreCallBack mCustomerCallBack;
    SwipeRefreshAndLoadMoreCallBack mBusinessOpportunityCallBack;
    SwipeRefreshAndLoadMoreCallBack mContractCallBack;

    View.OnClickListener mClueSearchListener;
    View.OnClickListener mCustomerSearchListener;
    View.OnClickListener mBusinessOpportunitySearchListener;
    View.OnClickListener mContractSearchListener;

    View mVClueEmpty;
    View mVCustomerEmpty;
    View mVBusinessEmpty;
    View mVContractEmpty;

    public AddFollowHomeAdapter(Context context) {
        this.mContext = context;
        mTabs = new String[]{mContext.getString(R.string.clue), mContext.getString(R.string.customer), mContext.getString(R.string.business_opportunity)
                , mContext.getString(R.string.contract)};
        mClueAdapter = new ClueRvAdapter(context, false);
        mCustomerAdapter = new CustomerRvAdapter(context, false);
        mBusinessOpportunityAdapter = new BusinessOpportunityRvAdapter(context);
        mContractAdapter = new ContractRvAdapter(context);
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.text_home_indicator, container, false);
        }
        convertView.findViewById(R.id.v_line).setVisibility(View.VISIBLE);
        if (position == getCount() - 1) {
            convertView.findViewById(R.id.v_line).setVisibility(View.GONE);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_tab);
        textView.setText(mTabs[position]);
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_add_follow_home, container, false);
        }
        View vSearch = convertView.findViewById(R.id.rl_search);
        RecyclerView rvContent = (RecyclerView) convertView.findViewById(R.id.rv_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvContent.setLayoutManager(layoutManager);
        SwipeRefreshLayout srlContainer = (SwipeRefreshLayout) convertView.findViewById(R.id.srl_container);
        srlContainer.setColorSchemeResources(Contants.colors);
        TextView tvSearchHint = (TextView) convertView.findViewById(R.id.tv_search_hint);
        switch (position) {
            case 0:
                tvSearchHint.setText(R.string.please_input_clue_name);
                if(mClueSearchListener != null){
                    vSearch.setOnClickListener(mClueSearchListener);
                }
                registerSwipeRefreshCallBack(layoutManager, srlContainer, rvContent, mClueCallBack, mClueAdapter);
                rvContent.setAdapter(mClueAdapter);
                mVClueEmpty = convertView.findViewById(R.id.ll_empty);
                if(mClueAdapter.getItemCount() == 0){
                    mVClueEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVClueEmpty.setVisibility(View.GONE);
                }
                break;
            case 1:
                tvSearchHint.setText(R.string.please_input_customer_name);
                if(mCustomerSearchListener != null){
                    vSearch.setOnClickListener(mCustomerSearchListener);
                }
                mVCustomerEmpty = convertView.findViewById(R.id.ll_empty);
                if(mCustomerAdapter.getItemCount() == 0){
                    mVCustomerEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVCustomerEmpty.setVisibility(View.GONE);
                }
                registerSwipeRefreshCallBack(layoutManager, srlContainer, rvContent, mCustomerCallBack, mCustomerAdapter);
                rvContent.setAdapter(mCustomerAdapter);
                break;
            case 2:
                tvSearchHint.setText(R.string.please_input_business_opportunity_name);
                if(mBusinessOpportunitySearchListener != null){
                    vSearch.setOnClickListener(mBusinessOpportunitySearchListener);
                }
                mVBusinessEmpty = convertView.findViewById(R.id.ll_empty);
                if(mBusinessOpportunityAdapter.getItemCount() == 0){
                    mVBusinessEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVBusinessEmpty.setVisibility(View.GONE);
                }
                registerSwipeRefreshCallBack(layoutManager, srlContainer, rvContent, mBusinessOpportunityCallBack, mBusinessOpportunityAdapter);
                rvContent.setAdapter(mBusinessOpportunityAdapter);
                break;
            case 3:
                tvSearchHint.setText(R.string.please_input_contract_name);
                if(mContractSearchListener != null){
                    vSearch.setOnClickListener(mContractSearchListener);
                }
                mVContractEmpty = convertView.findViewById(R.id.ll_empty);
                if(mContractAdapter.getItemCount() == 0){
                    mVContractEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVContractEmpty.setVisibility(View.GONE);
                }
                registerSwipeRefreshCallBack(layoutManager, srlContainer, rvContent, mContractCallBack, mContractAdapter);
                rvContent.setAdapter(mContractAdapter);
                break;
        }
        return convertView;
    }


    public void registerSwipeRefreshCallBack(final LinearLayoutManager layoutManager, final SwipeRefreshLayout srlContainer, RecyclerView rvContent, final SwipeRefreshAndLoadMoreCallBack callBack, final BaseWithFooterRvAdapter adapter) {
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlContainer = srlContainer;
                callBack.refresh();
            }
        });
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount() && adapter.isShowFooter()) {
                    //加载更多
                    callBack.loadMore();
                }
            }
        });
    }

    public void set4OnSearchClickListener(View.OnClickListener clueSearchListener, View.OnClickListener customeSearchListener,
                                          View.OnClickListener businessOpportunitySearchListner,View.OnClickListener contractSearchListener){
        mClueSearchListener = clueSearchListener;
        mCustomerSearchListener = customeSearchListener;
        mBusinessOpportunitySearchListener = businessOpportunitySearchListner;
        mContractSearchListener = contractSearchListener;
    }

    public void set4CallBack(SwipeRefreshAndLoadMoreCallBack clue, SwipeRefreshAndLoadMoreCallBack customer,
                             SwipeRefreshAndLoadMoreCallBack businessOpportunity, SwipeRefreshAndLoadMoreCallBack contract) {
        mClueCallBack = clue;
        mCustomerCallBack = customer;
        mBusinessOpportunityCallBack = businessOpportunity;
        mContractCallBack = contract;
    }

    public void set4OnRvItemClickListener(ClueRvAdapter.OnItemClickListener clue, ClueRvAdapter.OnItemClickListener customer,
                                          ClueRvAdapter.OnItemClickListener businessOpportunity, ClueRvAdapter.OnItemClickListener contract){
        mClueAdapter.setOnItemClickListener(clue);
        mCustomerAdapter.setOnItemClickListener(customer);
        mBusinessOpportunityAdapter.setOnItemClickListener(businessOpportunity);
        mContractAdapter.setOnItemClickListener(contract);
    }

    public void stopRefresh() {
        if(mSrlContainer != null){
            mSrlContainer.setRefreshing(false);
        }
    }

    public <T> void setData(List<T> list, boolean isRefresh, int type) {
        switch (type) {
            case Contants.TYPE_CLUE:
                mClueAdapter.setData((List<ClueParams>) list, isRefresh);
                if(list.isEmpty()){
                    mVClueEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVClueEmpty.setVisibility(View.GONE);
                }
                mClueAdapter.notifyDataSetChanged();
                break;
            case Contants.TYPE_CUSTOMER:
                mCustomerAdapter.setData((List<CustomerParams>) list, isRefresh);
                if(list.isEmpty()){
                    mVCustomerEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVCustomerEmpty.setVisibility(View.GONE);
                }
                mCustomerAdapter.notifyDataSetChanged();
                break;
            case Contants.TYPE_BUSINESS_OPPORTUNITY:
                mBusinessOpportunityAdapter.setData((List<BusinessOpportunityParams>) list, isRefresh);
                if(list.isEmpty()){
                    mVBusinessEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVBusinessEmpty.setVisibility(View.GONE);
                }
                mBusinessOpportunityAdapter.notifyDataSetChanged();
                break;
            case Contants.TYPE_CONTRACT:
                mContractAdapter.setData((List<ContractParams>) list, isRefresh);
                if(list.isEmpty()){
                    mVContractEmpty.setVisibility(View.VISIBLE);
                } else {
                    mVContractEmpty.setVisibility(View.GONE);
                }
                mContractAdapter.notifyDataSetChanged();
                break;
        }
    }


    public ClueParams getClueParams(int pos){
        return mClueAdapter.getParams(pos);
    }

    public CustomerParams getCustomerParams(int pos){
        return mCustomerAdapter.getParams(pos);
    }

    public BusinessOpportunityParams getBusinessOpportunityParams(int pos){
        return mBusinessOpportunityAdapter.getParams(pos);
    }

    public ContractParams getContractParams(int pos){
        return mContractAdapter.getParams(pos);
    }


}
