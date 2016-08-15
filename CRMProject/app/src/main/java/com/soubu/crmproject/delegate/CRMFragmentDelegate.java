package com.soubu.crmproject.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.CRMRvAdapter;

/**
 * Created by dingsigang on 16-8-9.
 */
public class CRMFragmentDelegate extends BaseFragmentDelegate {

    RecyclerView mRvHomePage;
    CRMRvAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_crm;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new CRMRvAdapter(getActivity());
        mRvHomePage = get(R.id.rv_crm);
        mRvHomePage.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvHomePage.setAdapter(mAdapter);
    }


}
