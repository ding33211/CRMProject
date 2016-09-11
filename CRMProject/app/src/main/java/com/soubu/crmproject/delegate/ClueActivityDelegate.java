package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ClueRvAdapter;
import com.soubu.crmproject.model.ClueParams;
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
        mAdapter = new ClueRvAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
    }

    public void setData(List<ClueParams> list, boolean isRefresh){
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    public void setOnRecyclerViewItemClickListener(ClueRvAdapter.OnItemClickListener listener){
        mAdapter.setOnItemClickListener(listener);
    }

    public ClueParams getClueParams(int pos){
        return mAdapter.getParams(pos);
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

    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack) {
        registerSwipeRefreshCallBack(callBack, mAdapter);
    }


}
