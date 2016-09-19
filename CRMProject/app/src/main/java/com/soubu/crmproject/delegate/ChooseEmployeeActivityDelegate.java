package com.soubu.crmproject.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.crmproject.R;
import com.soubu.crmproject.adapter.ChooseEmployeeRvAdapter;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.EmployeeParams;
import com.soubu.crmproject.model.UserParams;
import com.soubu.crmproject.widget.RecyclerViewFastScroller;

import java.util.List;

/**
 * Created by dingsigang on 16-8-25.
 */
public class ChooseEmployeeActivityDelegate extends AppDelegate {
    ChooseEmployeeRvAdapter mAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.viewpager_add_follow_home;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mAdapter = new ChooseEmployeeRvAdapter(getActivity());
        get(R.id.srl_container).setEnabled(false);
        RecyclerView recyclerView = get(R.id.rv_content);
        recyclerView.setAdapter(mAdapter);
        final RecyclerViewFastScroller fastScroller = get(R.id.fast_scroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //TODO if the items are filtered, considered hiding the fast scroller here
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    if (firstVisibleItemPosition == -1)
                        //not initialized, or no items shown, so hide fast-scroller
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(mAdapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller, R.id.fast_scroller_bubble, R.id.fast_scroller_handle);

    }

    public void setOnItemClickListener(ChooseEmployeeRvAdapter.OnItemClickListener listener){
        mAdapter.setOnItemClickListener(listener);
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }

    //    public void setData(List<UserParams> list){
//        mAdapter.setData(list);
//        mAdapter.notifyDataSetChanged();
//    }
}
