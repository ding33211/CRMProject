package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.greendao.Clue;
import com.soubu.crmproject.base.mvp.view.AppDelegate;

import java.util.List;

/**
 * Created by dingsigang on 16-8-12.
 */
public class CustomerActivityDelegate extends BaseRecyclerViewActivityDelegate {

    @Override
    public void initWidget() {
        super.initWidget();
        getActivity().setTitle(R.string.customer);
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
