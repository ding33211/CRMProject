package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Clue;

import java.util.List;

/**
 * Created by dingsigang on 16-8-18.
 */
public class BusinessOpportunityDelegate extends BaseRecyclerViewActivityDelegate {
    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.business_opportunity);
    }


    public void setData(List<Clue> list){

    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    @Override
    public boolean ifNeedFilterOrSorter() {
        return true;
    }

    @Override
    public boolean ifNeedUseSwipeRefresh() {
        return true;
    }
}
