package com.soubu.crmproject.delegate;

import android.content.Intent;
import android.view.View;

import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.BusinessOpportunityRvAdapter;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.adapter.ContractRvAdapter;
import com.soubu.crmproject.adapter.CustomerRvAdapter;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.RetrofitRequest;
import com.soubu.crmproject.view.activity.BusinessOpportunityHomeActivity;
import com.soubu.crmproject.view.activity.ClueHomeActivity;
import com.soubu.crmproject.view.activity.ClueSpecActivity;
import com.soubu.crmproject.view.activity.ContractHomeActivity;
import com.soubu.crmproject.view.activity.CustomerHomeActivity;

import java.util.List;

/**
 * Created by dingsigang on 16-9-13.
 */
public class SearchActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private int mType;
    private ClueRvAdapter mClueAdapter;
    private CustomerRvAdapter mCustomerAdapter;
    private BusinessOpportunityRvAdapter mBusinessOpportunityRvAdapter;
    private ContractRvAdapter mContractRvAdapter;

    @Override
    public void initWidget() {
        super.initWidget();
        switch (mType){
            case Contants.FROM_CLUE:
                mClueAdapter = new ClueRvAdapter(getActivity().getApplicationContext(), false);
                setListAdapter(mClueAdapter);
                break;
            case Contants.FROM_CLUE_HIGH_SEAS:
                mClueAdapter = new ClueRvAdapter(getActivity().getApplicationContext(), true);
                setListAdapter(mClueAdapter);
                break;
            case Contants.FROM_CUSTOMER:
                mCustomerAdapter = new CustomerRvAdapter(getActivity().getApplicationContext());
                setListAdapter(mCustomerAdapter);
                break;
            case Contants.FROM_BUSINESS_OPPORTUNITY:
                mBusinessOpportunityRvAdapter = new BusinessOpportunityRvAdapter(getActivity().getApplicationContext());
                setListAdapter(mBusinessOpportunityRvAdapter);
                break;
            case Contants.FROM_CONTRACT:
                mContractRvAdapter = new ContractRvAdapter(getActivity().getApplicationContext());
                setListAdapter(mContractRvAdapter);
                break;
        }
    }


    public void setOnClueClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mClueAdapter.setOnItemClickListener(listener);
    }

    public void setOnClueRushClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mClueAdapter.setOnRushClickListener(listener);
    }

    public void setOnCustomerClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mCustomerAdapter.setOnItemClickListener(listener);
    }

    public void setOnBusinessOpportunityClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mBusinessOpportunityRvAdapter.setOnItemClickListener(listener);
    }

    public void setOnContractClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mContractRvAdapter.setOnItemClickListener(listener);
    }

    public void setType(int type){
        mType = type;
    }

    public ClueParams getClueParams(int pos){
        return mClueAdapter.getParams(pos);
    }

    public CustomerParams getCustomerParams(int pos){
        return mCustomerAdapter.getParams(pos);
    }

    public BusinessOpportunityParams getBusinessOpportunityParams(int pos){
        return mBusinessOpportunityRvAdapter.getParams(pos);
    }

    public ContractParams getContractParams(int pos){
        return mContractRvAdapter.getParams(pos);
    }

//    public void clear(){
//        switch (mType){
//            case Contants.FROM_CLUE:
//                mClueAdapter.clear();
//                break;
//            case Contants.FROM_CUSTOMER:
//                mCustomerAdapter.clear();
//                break;
//            case Contants.FROM_BUSINESS_OPPORTUNITY:
//                mBusinessOpportunityRvAdapter.clear();
//                break;
//            case Contants.FROM_CONTRACT:
//                mContractRvAdapter.clear();
//                break;
//        }
//    }

    public void setClue(List<ClueParams> list, boolean isRefresh){
        mClueAdapter.setData(list, isRefresh);
        mClueAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public void setCustomer(List<CustomerParams> list, boolean isRefresh){
        mCustomerAdapter.setData(list, isRefresh);
        mCustomerAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public void setBusinessOpportunity(List<BusinessOpportunityParams> list, boolean isRefresh){
        mBusinessOpportunityRvAdapter.setData(list, isRefresh);
        mBusinessOpportunityRvAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public void setContract(List<ContractParams> list, boolean isRefresh){
        mContractRvAdapter.setData(list, isRefresh);
        mContractRvAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    @Override
    public boolean ifNeedFilterOrSorter() {
        return false;
    }

    @Override
    public boolean ifNeedUseSwipeRefresh() {
        return false;
    }

    @Override
    public boolean ifNeedSearch() {
        return true;
    }
}
