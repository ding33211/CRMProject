package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.base.greendao.Clue;
import com.soubu.crmproject.model.ClueTest;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-8-16.
 */
public class ClueActivityDelegate extends BaseRecyclerViewActivityDelegate {

    private ClueRvAdapter mAdapter;


    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.clue);
        mAdapter = new ClueRvAdapter();
        setListAdapter(mAdapter);
    }

    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack){
        registerSwipeRefreshCallBack(callBack, mAdapter);
    }


    public void setData(List<ClueTest> list){
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
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
