package com.soubu.crmproject.delegate;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ContactRvAdapter;
import com.soubu.crmproject.model.ClueParams;
import com.soubu.crmproject.model.ContactParams;
import com.soubu.crmproject.widget.SwipeRefreshAndLoadMoreCallBack;

import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private ContactRvAdapter mAdapter;


    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.contact);
        mAdapter = new ContactRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<ContactParams> list, boolean isRefresh){
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
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
        return true;
    }

    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack) {
        registerSwipeRefreshCallBack(callBack, mAdapter);
    }
}
