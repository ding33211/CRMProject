package com.soubu.crmproject.delegate;

import android.support.v4.view.ViewPager;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.AddFollowHomeAdapter;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.BusinessOpportunityParams;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.model.ContractParams;
import com.soubu.crmproject.model.CustomerParams;
import com.soubu.crmproject.server.ObjectToMapInterface;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;
import com.soubu.crmproject.widget.indicatorviewpager.ColorBar;
import com.soubu.crmproject.widget.indicatorviewpager.Indicator;
import com.soubu.crmproject.widget.indicatorviewpager.IndicatorViewPager;
import com.soubu.crmproject.widget.indicatorviewpager.OnTransitionTextListener;

import java.util.List;

/**
 * Created by dingsigang on 16-9-5.
 */
public class AddFollowHomeActivityDelegate extends AppDelegate {
    AddFollowHomeAdapter mAdapter;
    OnPageChangeListener mListener;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_add_follow_home;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new AddFollowHomeAdapter(getActivity().getApplicationContext());
        ViewPager viewPager = get(R.id.vp_content);
        Indicator indicator = get(R.id.fiv_indicator);
        indicator.setScrollBar(new ColorBar(getActivity(), getActivity().getResources().getColor(R.color.colorPrimary), 5));
        float unSelectSize = 16;
        //字体都用一个大小
        float selectSize = unSelectSize;
        int selectColor = getActivity().getResources().getColor(R.color.colorPrimary);
        int unSelectColor = getActivity().getResources().getColor(R.color.filter_tab_text_color);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(mAdapter);
        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                if(mListener != null){
                    mListener.onPageChange(currentItem);
                }
            }
        });
    }

    public void set4SwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack clueCallBack, SwipeRefreshAndLoadMoreCallBack customerCallBack,
                                         SwipeRefreshAndLoadMoreCallBack businessOpportunityCallBack, SwipeRefreshAndLoadMoreCallBack contractCallBack){
        mAdapter.set4CallBack(clueCallBack, customerCallBack, businessOpportunityCallBack, contractCallBack);
    }

    public void stopRefresh(){
        mAdapter.stopRefresh();
    }

    public <T extends ObjectToMapInterface> void setData(List<T> list, Boolean isRefresh, int type){
        if(list == null){
            return;
        }
        switch (type){
            case Contants.TYPE_CLUE:
                mAdapter.setData(list, isRefresh, type);
                break;
            case Contants.TYPE_CUSTOMER:
                mAdapter.setData(list, isRefresh, type);
                break;
            case Contants.TYPE_BUSINESS_OPPORTUNITY:
                mAdapter.setData(list, isRefresh, type);
                break;
            case Contants.TYPE_CONTRACT:
                mAdapter.setData(list, isRefresh, type);
                break;
        }
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    public interface OnPageChangeListener{
        void onPageChange(int currentPos);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener){
        mListener = listener;
    }

    public void setOnRecyclerViewItemClickListener(ClueRvAdapter.OnItemClickListener clueListener, ClueRvAdapter.OnItemClickListener customerListener,
                                                   ClueRvAdapter.OnItemClickListener businessOpportuityListener, ClueRvAdapter.OnItemClickListener contractListener){
        mAdapter.set4OnRvItemClickListener(clueListener, customerListener, businessOpportuityListener, contractListener);
    }

    public ClueParams getClueParams(int pos){
        return mAdapter.getClueParams(pos);
    }

    public CustomerParams getCustomerParams(int pos){
        return mAdapter.getCustomerParams(pos);
    }

    public BusinessOpportunityParams getBusinessOpportunityParams(int pos){
        return mAdapter.getBusinessOpportunityParams(pos);
    }

    public ContractParams getContractParams(int pos){
        return mAdapter.getContractParams(pos);
    }



}
