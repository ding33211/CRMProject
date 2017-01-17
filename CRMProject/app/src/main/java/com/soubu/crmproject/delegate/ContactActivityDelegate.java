package com.soubu.crmproject.delegate;

import com.soubu.crmproject.adapter.BaseWithFooterRvAdapter;
import com.soubu.crmproject.adapter.ContactRvAdapter;
import com.soubu.crmproject.model.ContactParams;

import java.util.List;

/**
 * Created by dingsigang on 16-9-8.
 */
public class ContactActivityDelegate extends BaseRecyclerViewActivityDelegate {
    private ContactRvAdapter mAdapter;


    @Override
    public void initWidget() {
        super.initWidget();
//        setTitle(R.string.contact);
        mAdapter = new ContactRvAdapter();
        setListAdapter(mAdapter);
    }

    public void setData(List<ContactParams> list, boolean isRefresh){
        mAdapter.setData(list, isRefresh);
        mAdapter.notifyDataSetChanged();
        ifDataEmpty(list.isEmpty());
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

//    @Override
//    public boolean ifNeedFilterOrSorter() {
//        return false;
//    }

//    @Override
//    public boolean ifNeedUseSwipeRefresh() {
//        return true;
//    }

//    public void registerSwipeRefreshCallBack(SwipeRefreshAndLoadMoreCallBack callBack) {
//        registerSwipeRefreshCallBack(callBack, mAdapter);
//    }

    public void setOnRecyclerViewItemClickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mAdapter.setOnItemClickListener(listener);
    }

    public void setOnPhoneIconCLickListener(BaseWithFooterRvAdapter.OnItemClickListener listener){
        mAdapter.setOnPhoneIconClickListener(listener);
    }

    public ContactParams getContactParams(int pos){
        return mAdapter.getParams(pos);
    }

    @Override
    public boolean ifNeedHideToolBar() {
        return true;
    }
}
